import streamlit as st
import numpy as np
import pandas as pd

# ─────────────────────────────────────────────
# DATA
# ─────────────────────────────────────────────
df       = pd.read_csv("data_wifi.csv")
ALTS     = df.iloc[:, 0].tolist()
CRITERIA = df.columns[1:].tolist()
MATRIX   = df.iloc[:, 1:].to_numpy(dtype=float)
N        = len(CRITERIA)

# Tipe kriteria (hardcoded)
TYPES = {
    "Ping_latency"         : "cost",
    "Download_speed"       : "benefit",
    "Upload_speed"         : "benefit",
    "Packet_loss_rate"     : "cost",
    "Router_distance"      : "benefit",
    "Network_congestion"   : "cost",
    "ISP_quality"          : "benefit",
    "Connection_type_DSL"  : "benefit",
    "Connection_type_Cable": "benefit",
    "Connection_type_Fiber": "benefit",
    "Signal_strength"      : "benefit",
    "Weather_conditions"   : "cost",
    "Internet_speed"       : "benefit",
}
types = [TYPES[c] for c in CRITERIA]

RI_TABLE = {1:0.00, 2:0.00, 3:0.58, 4:0.90, 5:1.12,
            6:1.24, 7:1.32, 8:1.41, 9:1.45, 10:1.49}

# ─────────────────────────────────────────────
# AHP HELPERS
# ─────────────────────────────────────────────
def build_ahp_matrix_from_weights(weights):
    """
    Bangun matriks perbandingan berpasangan dari bobot individual.
    Rumus: m[i][j] = weights[i] / weights[j]
    Diagonal otomatis = 1, dan m[j][i] = 1 / m[i][j].
    """
    n = len(weights) # mengambil jumlah weight ada berapa
    m = np.ones((n, n)) # membuat jadi n/n agar m itu 1
    for i in range(n):
        for j in range(n):
            if i != j: # agar tidak kriteria sama, karena diagonal pasti 1
                m[i, j] = weights[i] / weights[j] # rumus perbandingan
    return m

def ahp_compute(m):
    """Hitung bobot eigen, λmax, CI, RI, dan CR."""
    n    = m.shape[0] # Ambil bentuk matriks
    w    = (m / m.sum(axis=0)).mean(axis=1) # m.sum agar menambahkan perkolom (normalisasi bobot) kemudian dibuat rata2
    lmax = (m @ w / w).mean() # m dikali matriks w kemudian di cari rata rata
    CI   = (lmax - n) / (n - 1) 
    RI   = RI_TABLE.get(n, 1.49) 
    CR   = CI / RI if RI > 0 else 0.0
    return w, lmax, CI, RI, CR

def ahp_score(data, w, types):
    """Normalisasi data lalu hitung skor AHP per alternatif."""
    norm = np.zeros_like(data)
    for j, t in enumerate(types):
        col        = 1.0 / data[:, j] if t == "cost" else data[:, j]
        norm[:, j] = col / col.sum()
    return norm @ w

def ranking(scores):
    return pd.Series(scores).rank(ascending=False).astype(int).to_numpy()

# ─────────────────────────────────────────────
# PAGE 1 — DATA ALTERNATIF
# ─────────────────────────────────────────────
def page_data():
    st.title("Data Alternatif")
    st.caption("Sistem pendukung keputusan pemilihan WiFi terbaik berdasarkan beberapa kriteria utama.")
    st.dataframe(
        pd.DataFrame(MATRIX, index=ALTS, columns=CRITERIA),
        use_container_width=True
    )

    # ── Heatmap Matriks Normalisasi ──
    st.subheader("Visualisasi Perbandingan Antar Alternatif")
    st.caption("Warna menunjukkan nilai relatif tiap kriteria. Hijau = nilai tinggi, Merah = nilai rendah.")

    df_display = pd.DataFrame(MATRIX, index=ALTS, columns=CRITERIA)

    st.dataframe(
        df_display.style.background_gradient(
            cmap="RdYlGn",
            axis=0          # normalisasi per kolom (per kriteria)
        ).format("{:.2f}"),
        use_container_width=True,
        height=500
    )
    
    st.subheader("Keterangan Kriteria")
    COLOR = {"cost": "#c0392b", "benefit": "#27ae60"}
    DESC  = {"cost": "Semakin kecil semakin baik", "benefit": "Semakin besar semakin baik"}

    cols_per_row = 4
    for row_start in range(0, N, cols_per_row):
        row_crits = CRITERIA[row_start : row_start + cols_per_row]
        row_types = types[row_start : row_start + cols_per_row]
        for col, crit, typ in zip(st.columns(len(row_crits)), row_crits, row_types):
            with col:
                st.markdown(
                    f'<div style="background:{COLOR[typ]};padding:12px;border-radius:8px;'
                    f'color:white;margin-bottom:8px">'
                    f'<b>{crit}</b><br>Tipe: <code>{typ}</code><br>'
                    f'<small>{DESC[typ]}</small></div>',
                    unsafe_allow_html=True
                )

# ─────────────────────────────────────────────
# PAGE 2 — AHP
# ─────────────────────────────────────────────
def page_ahp():
    st.title("Metode AHP")

    # ── 2.1 Input nilai tiap kriteria ──
    st.subheader("2.1 Nilai Kepentingan Tiap Kriteria")
    st.info(
        "Tentukan nilai kepentingan untuk setiap kriteria (skala 1–5). "
        "Matriks perbandingan berpasangan akan dihitung otomatis dengan rumus: "
        "**m[i][j] = nilai[i] / nilai[j]**"
    )

    # Slider 1–5 untuk tiap kriteria, tampil dalam grid 4 kolom
    crit_weights = []
    cols_per_row = 4
    for row_start in range(0, N, cols_per_row):
        row_crits = CRITERIA[row_start : row_start + cols_per_row]
        for col, crit in zip(st.columns(len(row_crits)), row_crits):
            with col:
                v = st.slider(crit, min_value=1, max_value=5, value=3, step=1, key=f"w_{crit}")
                crit_weights.append(v)

    crit_weights = np.array(crit_weights, dtype=float)

    # ── 2.2 Matriks perbandingan berpasangan (otomatis) ──
    st.subheader("2.2 Matriks Perbandingan Berpasangan")
    st.caption("Dihitung otomatis dari nilai kepentingan: m[i][j] = nilai[i] / nilai[j]")
    m = build_ahp_matrix_from_weights(crit_weights)
    st.dataframe(
        pd.DataFrame(m.round(4), index=CRITERIA, columns=CRITERIA),
        use_container_width=True
    )

    # ── 2.3 Bobot prioritas ──
    w, lmax, CI, RI, CR = ahp_compute(m)

    st.subheader("2.3 Bobot Prioritas (Eigen Vector)")
    st.dataframe(
        pd.DataFrame({"Kriteria": CRITERIA, "Bobot": w.round(4)}),
        use_container_width=True, hide_index=True
    )

    # ── 2.4 Nilai konsistensi ──
    st.subheader("2.4 Nilai Konsistensi")
    c1, c2, c3, c4 = st.columns(4)
    c1.metric("λ max", f"{lmax:.4f}")
    c2.metric("CI",    f"{CI:.4f}")
    c3.metric("RI",    f"{RI}")
    c4.metric("CR",    f"{CR:.4f}")

    if CR <= 0.1:
        st.success(f"Konsisten — CR = {CR:.4f} ≤ 0.10")
    else:
        st.error(f"Tidak Konsisten — CR = {CR:.4f} > 0.10. Pertimbangkan revisi nilai kepentingan.")

    # ── 2.5 Skor akhir & ranking ──
    st.subheader("2.5 Skor Akhir dan Ranking")
    scores  = ahp_score(MATRIX, w, types)
    df_rank = (
        pd.DataFrame({"ISP": ALTS, "Skor AHP": scores.round(4), "Ranking": ranking(scores)})
        .sort_values("Ranking")
        .reset_index(drop=True)
    )
    st.dataframe(df_rank, use_container_width=True, hide_index=True)
    best = df_rank.iloc[0]
    st.success(f"ISP terbaik: **{best['ISP']}** — Skor AHP = **{best['Skor AHP']}**")
    
    chart_data = df_rank.set_index("ISP")[["Skor AHP"]]
    st.bar_chart(chart_data)

# ─────────────────────────────────────────────
# MAIN
# ─────────────────────────────────────────────
st.set_page_config(page_title="SCPK WiFi", layout="wide")

st.sidebar.header("Navigasi")
page = st.sidebar.selectbox(
    "Pilih Halaman",
    ["Page 1 — Data Alternatif", "Page 2 — AHP"]
)

if "1" in page:
    page_data()
elif "2" in page:
    page_ahp()