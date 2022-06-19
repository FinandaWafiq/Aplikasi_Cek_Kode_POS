package com.example.loginkodepos.model;

public class PosModel {
    String id, Provinsi, Kabupaten, Kecamatan, Kelurahan, KodePOS;

    public PosModel(String provinsi, String kabupaten, String kecamatan, String kelurahan, String kodePOS) {
        Provinsi = provinsi;
        Kabupaten = kabupaten;
        Kecamatan = kecamatan;
        Kelurahan = kelurahan;
        KodePOS = kodePOS;
    }

    public PosModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvinsi() {
        return Provinsi;
    }

    public void setProvinsi(String provinsi) {
        Provinsi = provinsi;
    }

    public String getKabupaten() {
        return Kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        Kabupaten = kabupaten;
    }

    public String getKecamatan() {
        return Kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        Kecamatan = kecamatan;
    }

    public String getKelurahan() {
        return Kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        Kelurahan = kelurahan;
    }

    public String getKodePOS() {
        return KodePOS;
    }

    public void setKodePOS(String kodePOS) {
        KodePOS = kodePOS;
    }
}
