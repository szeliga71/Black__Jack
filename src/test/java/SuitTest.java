import org.example.Suit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SuitTest {
    @Test
    void fromStringToSuitHappyHearts(){
        Assertions.assertEquals(Suit.HEARTS, Suit.valueOf("HEARTS"));
    }
    @Test
    void fromStringToSuitHappyClubs(){
        Assertions.assertEquals(Suit.CLUBS, Suit.valueOf("CLUBS"));
    }
    @Test
    void fromStringToSuitHappySpades(){
        Assertions.assertEquals(Suit.SPADES, Suit.valueOf("SPADES"));
    }
    @Test
    void fromStringToSuitHappyDiamonds(){
        Assertions.assertEquals(Suit.DIAMONDS, Suit.valueOf("DIAMONDS"));
    }
    @Test
    void fromStringToSuitInvalidInput(){
        String illegalText="xxxxxxx";
        Assertions.assertThrows(IllegalArgumentException.class, () -> Suit.fromStringtoSuit(illegalText));
    }
    @Test
    void fromStringToSuitEmptyInput(){
        String illegalText="";
        Assertions.assertThrows(IllegalArgumentException.class, () -> Suit.fromStringtoSuit(illegalText));
    }
    @Test
    void fromStringToSuitInvalidInput1() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> Suit.fromStringtoSuit("invalid"));
        Assertions.assertEquals("Cannot parse invalid", exception.getMessage());
    }
}
