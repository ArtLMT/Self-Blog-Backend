package com.lmt.selfblog.common;

public enum Language {
    EN,
    VI;

    public static Language fromString(String lang) {
        if (lang == null || lang.isBlank()) {
            return EN; // Default fallback
        }
        try {
            return Language.valueOf(lang.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return EN; // Default fallback
        }
    }
}
