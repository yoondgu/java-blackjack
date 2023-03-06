package blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class Players {

    private final static int COUNT_MINIMUM = 2;
    private final static int COUNT_MAXIMUM = 8;

    private final List<Player> players;

    public Players(List<Player> players) {
        validatePlayersCount(players);
        this.players = players;
    }

    private void validatePlayersCount(List<Player> players) {
        if (players.size() < COUNT_MINIMUM || players.size() > COUNT_MAXIMUM) {
            throw new IllegalArgumentException("플레이어 인원 수는 최소 " + COUNT_MINIMUM + "명 최대 " + COUNT_MAXIMUM + "명입니다.");
        }
    }

    public List<String> findAvailablePlayerNames() {
        List<String> availablePlayerNames = new ArrayList<>();
        for (Player player : players) {
            addAvailablePlayer(player, availablePlayerNames);
        }
        return availablePlayerNames;
    }

    private void addAvailablePlayer(Player player, List<String> availablePlayerNames) {
        if (player.isAvailable()) {
            availablePlayerNames.add(player.getName());
        }
    }

    public Player findPlayerBy(String playerName) {
        return players.stream()
                .filter(player -> player.getName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 이름을 가진 플레이어를 찾을 수 없습니다."));
    }

    public List<Player> players() {
        return new ArrayList<>(players);
    }
}
