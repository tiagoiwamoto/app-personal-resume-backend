package io.github.tiagoiwamoto.apppersonalresumebackend.core.entity;

import lombok.Getter;

@Getter
public enum EscolaEnum {

    TREINAWEB("TreinaWeb"),
    ALURA("Alura"),
    CURSOU("Cursou"),
    EDX("edX"),
    COURSERA("Coursera"),
    UDEMY("Udemy"),
    LINUXTIPS("Linux Tips"),
    EVG("Escola virtual governo"),
    AWS_TRAINING("AWS Training"),
    GOOGLE_TRAINING("Google Training"),
    MICROSOFT_TRAINING("Microsoft Training"),

    UNIOPET("Centro universitário Opet"),
    UP("Universidade Positivo"),
    UNICIV("Centro universitário UNICIV"),
    XP("Faculdade XP Educação"),
    ANHANGUERA("Faculdade Anhanguera");

    private String nome;

    EscolaEnum(String nome) {
        this.nome = nome;
    }
}
