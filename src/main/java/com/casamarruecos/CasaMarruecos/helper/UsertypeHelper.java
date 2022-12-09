package com.casamarruecos.CasaMarruecos.helper;

public enum UsertypeHelper {
    ADMIN(1L), REVIEWER(2L), DEVELOPER(3L);
    private final Long usertype;

    UsertypeHelper(Long usertype) {
        this.usertype = usertype;
    }

    public Long getUsertype() {
        return usertype;
    }       

}
