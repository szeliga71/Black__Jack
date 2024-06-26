package org.example.model;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    private final int value;
    private final Suit suit;

    @JsonCreator
    public Card(@JsonProperty("value") int value, @JsonProperty("suit") Suit suit) {
        this.value = value;
        this.suit = suit;
    }
    public int getValue() {
        return value;
    }

    public Suit getSuite() {
        return suit;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return getValue() == card.getValue() && getSuite().equals(card.getSuite());
    }
    @Override
    public int hashCode() {
        int result = getValue();
        result = 31 * result + getSuite().hashCode();
        return result;
    }
    @Override
    public String toString() {
        return "Card{" +
                "value=" + value +
                ", suit=" + suit +
                '}';
    }
}