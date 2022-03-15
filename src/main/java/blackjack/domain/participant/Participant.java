package blackjack.domain.participant;

import blackjack.domain.card.Card;
import blackjack.domain.card.ParticipantCards;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Participant {

    private static final int BUST_THRESHOLD_NUMBER = 21;

    protected ParticipantCards participantCards;

    public void receiveInitCards(List<Card> cards) {
        participantCards = new ParticipantCards(new ArrayList<>(cards));
    }

    public void receiveCard(Card card) {
        participantCards.addCard(card);
    }

    public int getScore() {
        return participantCards.calculateScore();
    }

    public boolean isBust() {
        return getScore() > BUST_THRESHOLD_NUMBER;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(participantCards.getCards());
    }

    public abstract boolean isHittable();

    public abstract String getName();

}
