package blackjack.domain.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardGroup {
    public static final int BLACKJACK_NUMBER = 21;
    private static final int ACE_SPECIAL_SCORE = 10;
    private static final int FIRST_CARD_INDEX = 0;
    private static final int SECOND_CARD_INDEX = 1;

    private final List<Card> cards;

    public CardGroup() {
        this.cards = new ArrayList<>();
    }

    public CardGroup(List<Card> cards) {
        this.cards = cards;
    }

    private CardGroup(CardGroup cardGroup) {
        this.cards = List.copyOf(cardGroup.cards);
    }

    public CardGroup copy() {
        return new CardGroup(this);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public boolean isBust() {
        return calculateScore() > BLACKJACK_NUMBER;
    }

    public boolean isNotBust() {
        return calculateScore() < BLACKJACK_NUMBER;
    }

    public int calculateScore() {
        int score = calculateSumWithMaximumAce();
        int aCount = countAce();
        while (isBustByAce(score, aCount)) {
            score -= ACE_SPECIAL_SCORE;
            aCount--;
        }
        return score;
    }

    private int calculateSumWithMaximumAce() {
        int sum = cards.stream()
                .map(Card::getCardNumber)
                .mapToInt(CardNumber::getNumber)
                .sum();
        return sum + countAce() * ACE_SPECIAL_SCORE;
    }

    private boolean isBustByAce(int score, int aCount) {
        return score > BLACKJACK_NUMBER && aCount > 0;
    }

    private int countAce() {
        return (int) cards.stream()
                .filter(Card::isAce)
                .count();
    }

    public boolean isBlackJack() {
        CardGroup initialCardGroup = new CardGroup(cards.subList(FIRST_CARD_INDEX, SECOND_CARD_INDEX + 1));
        return initialCardGroup.calculateScore() == BLACKJACK_NUMBER;
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}