package blackjack.domain.participant;

import java.util.Collections;
import java.util.List;

import blackjack.domain.card.Card;
import blackjack.domain.card.ParticipantCards;

public abstract class Participant {

    private final ParticipantName name;
    protected ParticipantCards cards;

    public Participant(String name) {
        this.name = new ParticipantName(name);
        this.cards = new ParticipantCards();
    }

    public void initCards(List<Card> cards) {
        this.cards.addInitialCards(cards);
    }

    public boolean isBlackjack() {
        return cards.isBlackjack();
    }

    public void addCard(Card card) {
        cards.addCard(card);
    }

    public boolean isBust() {
        return cards.isBust();
    }

    public int getScore() {
        return cards.getScore();
    }

    public String getName() {
        return name.getValue();
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards.getCards());
    }
}