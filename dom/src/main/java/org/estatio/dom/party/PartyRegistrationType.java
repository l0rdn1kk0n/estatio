package org.estatio.dom.party;

public enum PartyRegistrationType {

    VAT("VAT"), CHAMBER_OF_COMMERCE("Chamber of Commerce");

    private final String title;

    PartyRegistrationType(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }

}
