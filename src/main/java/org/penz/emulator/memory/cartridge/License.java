package org.penz.emulator.memory.cartridge;

public enum License {
    NONE(0x00),
    NINTENDO(0x01),
    CAPCOM(0x08),
    ELECTRONIC_ARTS(0x13),
    HUDSON_SOFT(0x18),
    B_AI(0x19),
    KSS(0x20),
    POW(0x22),
    PCM_COMPLETE(0x24),
    SAN_X(0x25),
    KEMCO_JAPAN(0x28),
    SETA(0x29),
    VIACOM(0x30),
    NINTENDO_2(0x31),
    BANDAI(0x32),
    OCEAN_ACCLAIM(0x33),
    KONAMI(0x34),
    HECTOR(0x35),
    TAITO(0x37),
    HUDSON(0x38),
    BANPRESTO(0x39),
    UBI_SOFT(0x41),
    ATLUS(0x42),
    MALIBU(0x44),
    ANGEL(0x46),
    BULLET_PROOF(0x47),
    IREM(0x49),
    ABSOLUTE(0x50),
    ACCLAIM(0x51),
    ACTIVISION(0x52),
    AMERICAN_SAMMY(0x53),
    KONAMI_2(0x54),
    HI_TECH_ENTERTAINMENT(0x55),
    LJN(0x56),
    MATCHBOX(0x57),
    MATTAL(0x58),
    MILTON_BRADLEY(0x59),
    TITUS(0x60),
    VIRGIN(0x61),
    LUCASARTS(0x64),
    OCEAN(0x67),
    ELECTRONIC_ARTS_2(0x69),
    INFOGRAMES(0x70),
    INTERPLAY(0x71),
    BRODERBUND(0x72),
    SCULPTURED(0x73),
    SCI(0x75),
    THQ(0x78),
    ACCOLADE(0x79),
    MISAWA(0x80),
    LOZC(0x83),
    TOKUMA_SHOTEN_INTERMEDIA(0x86),
    TSUKUDA_ORI(0x87),
    CHUN_SOFT(0x91),
    VIDEO_SYSTEM(0x92),
    OCEAN_2(0x93),
    VARIE(0x95),
    YONEZAWA(0x96),
    KANEKO(0x97),
    PACK_IN_SOFT(0x99),
    BOTTOM_UP(0xA0),
    KONAMI_YU_GI_OH(0xA4);

    public final int code;

    License(int code) {
        this.code = code;
    }

    public String getName() {
        return this.name().replace("_", " ");
    }
}
