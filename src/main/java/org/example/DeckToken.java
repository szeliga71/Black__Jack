package org.example;

public class DeckToken {

    private String deckID;

    public DeckToken(String deckID) {
        this.deckID = deckID;
    }

    public DeckToken() {
    }

    public String getDeckID() {
        return deckID;
    }

    public void setDeckID(String deckID) {
        this.deckID = deckID;
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeckToken deckToken)) return false;

        return getDeckID().equals(deckToken.getDeckID());
    }

    @Override
    public int hashCode() {
        return getDeckID().hashCode();
    }
}
