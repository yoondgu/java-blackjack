package blackjack.domain.result;

import blackjack.domain.card.Card;
import blackjack.domain.participants.Participant;
import java.util.ArrayList;
import java.util.List;

public class FinalCards {

    private final List<Card> cards;
    private final int sum;

    private FinalCards(final List<Card> cards, final int sum) {
        this.cards = cards;
        this.sum = sum;
    }

    public static FinalCards of(final Participant participant) {
        return new FinalCards(participant.getCards(), participant.computeCardsScore());
    }

    public List<Card> getCards() {
        return new ArrayList<>(cards);
    }

    public int getSum() {
        return sum;
    }
}