package Views;

import Models.Cards.Card;
import Models.Hero.Hero;
import Models.FileManagers.CardsFileManager;
import Models.Logger;
import Models.Player;
import Models.FileManagers.ProfilesFileManager;

import java.util.Scanner;

public class CLI {

    private Scanner sc = new Scanner(System.in);
    private Player player;
    private Hero hero = null;
    private String state = "";
    private Logger logger = Logger.getLogger();


    public void start() {
        while (true) {
            String query = "";
            if (!state.equals("") && !state.equals("login") && !state.equals("register")) {
                query = sc.nextLine();
            }
            stateManager(query);
        }
    }

    private void stateManager(String query) {
        if (state.equals("menu")) {
            menu(query);
        } else if (state.equals("collections")) {
            collections(query);
        } else if (state.equals("store")) {
            store(query);
        } else if (state.equals("")) {
            loginOrRegister();
        } else if (state.equals("login")) {
            login();
        } else if (state.equals("register")) {
            register();
        }
    }

    private void loginOrRegister() {
        System.out.println("already have an account?(y/n)");
        String query = sc.nextLine();
        if (query.equals("y")) {
            state = "login";
        } else if (query.equals("n")) {
            state = "register";
        } else if (query.equals("exit")) {
            System.exit(0);
        } else {
            System.out.println("please try again");
            state = "";
        }
    }

    private void register() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        if (!ProfilesFileManager.getProfilesFileManager().doesPlayerNameExists(username)) {
            this.state = "menu";
            ProfilesFileManager.getProfilesFileManager().createNewPlayer(username, password);
            this.player = ProfilesFileManager.getProfilesFileManager().getPlayer(username);
            logger.createPlayerLog(player);
            System.out.println("Menu");
        } else {
            System.out.println("the username already exists, please choose another username");
            this.state = "register";
        }
    }

    private void login() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        if (ProfilesFileManager.getProfilesFileManager().checkPlayer(username, password)) {
            this.state = "menu";
            this.player = ProfilesFileManager.getProfilesFileManager().getPlayer(username);
            System.out.println("Menu");
            logger.log("login", "logged in", player);
        } else {
            System.out.println("you provided the wrong info");
            this.state = "";
        }
    }


    public void menu(String query) {
        if (query.toLowerCase().equals("collections")) {
            System.out.println("Collections");
            this.state = "collections";
            logger.log("navigate", "menu to collections", player);
        } else if (query.toLowerCase().equals("store")) {
            System.out.println("Store");
            this.state = "store";
            logger.log("navigate", "menu to store", player);
        } else if (query.toLowerCase().equals("exit -a")) {
            ProfilesFileManager.getProfilesFileManager().updatePlayer(player);
            logger.log("exit", "closing the program", player);
            System.exit(0);
        } else if (query.toLowerCase().equals("exit")) {
            this.state = "";
            logger.log("logout", "logging out of account", player);
            ProfilesFileManager.getProfilesFileManager().updatePlayer(player);
        } else if (query.toLowerCase().equals("delete-player")) {
            deletePlayerHandler();
        } else if (query.equals("hearthstone --help")) {
            help();
        } else {
            System.out.println("What?!");
            logger.log("unknown command", "command:" + query, player);
        }
    }

    private void deletePlayerHandler() {
        System.out.print("Password: ");
        String password = sc.nextLine();
        if (this.player.getPlayerPasswor().equals(password)) {
            this.state = "";
            ProfilesFileManager.getProfilesFileManager().removePlayer(player.getPlayerName());
            logger.endOfLog(player);
            this.player = null;
        } else {
            System.out.println("password is wrong, back to menu");
        }
    }

    public void store(String query) {
        if (query.equals("exit -a")) {
            ProfilesFileManager.getProfilesFileManager().updatePlayer(player);
            logger.log("exit", "closing the program", player);
            System.exit(0);
        }
        String[] parts = query.split(" ");
        if (parts[0].equals("exit")) {
            System.out.println("Menu");
            state = "menu";
            ProfilesFileManager.getProfilesFileManager().updatePlayer(player);
            logger.log("navigate", "store to menu", player);
        } else if (parts[0].equals("wallet") && parts.length == 1) {
            System.out.println(player.getPlayerGolds());
            logger.log("wallet", "getting total golds", player);
        } else if (parts[0].equals("buy") && parts.length >= 2) {
            storeBuyHandler(parts);
        } else if (parts[0].equals("sell") && parts.length >= 2) {
            storeSellHandler(parts);
        } else if (parts[0].equals("ls") && parts.length == 2) {
            storeListHandler(parts);
        } else if (parts[0].equals("hearthstone") && parts.length == 2) {
            if (parts[1].equals("--help")) {
                help();
            }
        } else {
            logger.log("unknown command", "command: " + query, player);
            System.out.println("please try again");
        }
    }

    private void collections(String query) {
        if (query.equals("exit -a")) {
            ProfilesFileManager.getProfilesFileManager().updatePlayer(player);
            logger.log("exit", "closing the program", player);
            System.exit(0);
        }
        String[] parts = query.split(" ");
        if (parts[0].equals("exit")) {
            System.out.println("Menu");
            state = "menu";
            ProfilesFileManager.getProfilesFileManager().updatePlayer(player);
            logger.log("navigate", "collections to menu", player);
        } else if (parts[0].equals("ls") && parts.length == 3) {
            collectionsListHandler(parts);
        } else if (parts[0].equals("add") && parts.length >= 2) {
            collectionsAddHandler(parts);
        } else if (parts[0].equals("remove") && parts.length >= 2) {
            collectionsRemoveHandler(parts);
        } else if (parts[0].equals("select") && parts.length == 2) {
            collectionsSelectHandler(parts);
        } else if (parts[0].equals("hearthstone") && parts.length == 2) {
            if (parts[1].equals("--help")) {
                help();
            }
        } else {
            System.out.println("please try again");
            logger.log("unknown command", "command: " + query, player);
        }
    }

    private void help() {
        if (state.equals("collections")) {
            System.out.println("ls -a -heroes : show all available heroes");
            System.out.println("ls -m -heroes : show the selected hero");
            System.out.println("ls -a -cards : show all owned cards");
            System.out.println("ls -m -cards : show all cards in the selected hero deck");
            System.out.println("ls -n -cards : show all cards that can be added to the deck");
            System.out.println("select heroName : select a hero");
            System.out.println("add [Card’s Name] : add a card to a deck");
            System.out.println("remove [Card’s Name] : remove a card from a deck");
            System.out.println("exit : goes to menu");
        }
        if (state.equals("store")) {
            System.out.println("buy [Card’s name] : buy a card from store");
            System.out.println("sell [Card’s name] : sell a card to store");
            System.out.println("wallet : show the player golds");
            System.out.println("ls -s : show sellable cards to store");
            System.out.println("ls -b : show buyable cards from store");
            System.out.println("exit : goes to menu");
        }
        if (state.equals("menu")) {
            System.out.println("collections : goes to collections");
            System.out.println("store : goes to store");
            System.out.println("exit : logout of account");
        }
    }

    private void collectionsListHandler(String[] parts) {
        if (parts[2].equals("-heroes") && parts[1].equals("-a")) {
            System.out.println("available heroes :");
            for (Hero hero : player.getAvailableHeros()) {
                System.out.println(hero.getName());
            }
            logger.log("list", "available heroes", player);
        } else if (parts[2].equals("-heroes") && parts[1].equals("-m")) {
            if (hero == null) {
                System.out.println("you haven't select any hero yet");
                logger.log("hero", "selected hero : haven't select any hero", player);
            } else {
                System.out.println(hero.getName());
                logger.log("hero", "selected hero", player);
            }
        } else if (parts[2].equals("-cards") && hero == null) {
            System.out.println("you haven't select any hero yet");
            logger.log("cards", "cards : haven't select any hero", player);
        } else if (parts[2].equals("-cards") && hero != null) {
            if (parts[1].equals("-a")) {
                System.out.println("All the cards you have :");
                for (Card card : player.getAvailableCards()) {
                    System.out.println(card.name);
                }
                logger.log("cards", "cards : all player cards", player);
            } else if (parts[1].equals("-m")) {
                System.out.println("Current cards in the deck :");
                for (Hero hero : player.getAvailableHeros()) {
                    if (hero.getName() == this.hero.getName()) {
//                        for (Card card : hero.getDeck().getCards()) {
//                            System.out.println(card.name);
//                        }
                    }
                }
                logger.log("cards", "cards : hero deck", player);
            } else if (parts[1].equals("-n")) {
                System.out.println("Cards you can add to the deck :");
//                for (Card card : player.addableCards(hero)) {
//                    System.out.println(card.name);
//                }
                logger.log("cards", "cards : addable cards", player);
            }
        }
    }

    private void collectionsAddHandler(String[] parts) {
//        String card = "";
//        for (int i = 1; i < parts.length; i++) {
//            card += parts[i] + " ";
//        }
//        parts[1] = card.substring(1, card.length() - 2);
//        if (hero == null) {
//            System.out.println("you haven't select any hero yet");
//            logger.log("add", "add : haven't select any hero", player);
//        } else if (!CardsFileManager.getCardsFileManager().cardExists(parts[1])) {
//            System.out.println(parts[1] + " is not a card !");
//            logger.log("add", "add : not a card", player);
//        } else if (!player.getHero(hero.getName()).isCardAddable(CardsFileManager.getCardsFileManager().getCard(parts[1]))) {
//            System.out.println("This card is already in your deck or is from other heroes");
//            logger.log("add", "add : card already in deck", player);
//        } else if (player.addCardToDeck(CardsFileManager.getCardsFileManager().getCard(parts[1]), this.hero)) {
//            System.out.println(parts[1] + " added to this deck");
//            logger.log("add", "add : card added to deck", player);
//        } else {
//            System.out.println(parts[1] + " couldn't be added to this deck");
//            logger.log("add", "add : something bad happened", player);
//        }
    }

    private void collectionsRemoveHandler(String[] parts) {
        String card = "";
        for (int i = 1; i < parts.length; i++) {
            card += parts[i] + " ";
        }
        parts[1] = card.substring(1, card.length() - 2);
//        if (hero == null) {
//            System.out.println("you haven't select any hero yet");
//            logger.log("remove", "remove : haven't select any hero", player);
//        } else if (!CardsFileManager.getCardsFileManager().cardExists(parts[1])) {
//            System.out.println(parts[1] + " is not a card");
//            logger.log("remove", "remove : not a card", player);
//        } else if (!player.isCardInDeck(CardsFileManager.getCardsFileManager().getCard(parts[1]), this.hero)) {
//            System.out.println(parts[1] + " is not in this deck");
//            logger.log("remove", "remove : already not in the deck", player);
//        } else {
//            if (player.removeCardFromDeck(CardsFileManager.getCardsFileManager().getCard(parts[1]), this.hero)) {
//                System.out.println(parts[1] + " removed from this deck");
//                logger.log("remove", "remove : card removed from deck", player);
//            } else {
//                System.out.println(parts[1] + " couldn't be removed from your deck");
//                logger.log("remove", "remove : something bad happened", player);
//            }
//        }
    }

    private void collectionsSelectHandler(String[] parts) {
        if (player.getHero(parts[1]) != null) {
            System.out.println(parts[1] + " is selected");
            hero = player.getHero(parts[1]);
            logger.log("select", "select: " + parts[1] + " selected", player);
        } else {
            System.out.println(parts[1] + " is not an available hero");
            logger.log("select", "select: " + parts[1] + " was not available", player);
        }
    }

    private void storeBuyHandler(String[] parts) {
        String card = "";
        for (int i = 1; i < parts.length; i++) {
            card += parts[i] + " ";
        }
        parts[1] = card.substring(1, card.length() - 2);
        if (!CardsFileManager.getCardsFileManager().cardExists(parts[1])) {
            System.out.println(parts[1] + " is not a card");
            logger.log("buy", "not a valid card", player);
        } else if (this.player.doesHaveCard(parts[1])) {
            System.out.println("You already have " + parts[1]);
            logger.log("buy", "already have the card", player);
        } else {
            System.out.println(parts[1] + " costs: " + CardsFileManager.getCardsFileManager().getCard(parts[1]).cost());
            System.out.print("Continue?(y/n)");
            String answer = sc.nextLine();
            if (answer.equals("n")) {
                System.out.println("Back to store");
                logger.log("buy", "didn't accept the buy", player);
            } else if (answer.equals("y")) {
                if (player.getPlayerGolds() < CardsFileManager.getCardsFileManager().getCard(parts[1]).cost()) {
                    System.out.println("You don't have enough golds");
                    logger.log("buy", "didn't have enough gold", player);
                } else {
                    int cost = CardsFileManager.getCardsFileManager().getCard(parts[1]).cost();
                    player.getAvailableCards().add(CardsFileManager.getCardsFileManager().getCard(parts[1]));
                    player.setPlayerGolds(player.getPlayerGolds() - cost);
                    System.out.println("The card has been added to your cards");
                    logger.log("buy", parts[1] + " added to cards", player);
                    logger.log("gold", cost + " gold removed from player", player);
                }
            } else {
                System.out.println("No buy, back to store");
                logger.log("unknown command", "buy has been cancelled", player);
            }
        }
    }

    private void storeSellHandler(String[] parts) {
        String card = "";
        for (int i = 1; i < parts.length; i++) {
            card += parts[i] + " ";
        }
        parts[1] = card.substring(1, card.length() - 2);
        if (!CardsFileManager.getCardsFileManager().cardExists(parts[1])) {
            System.out.println(parts[1] + " is not a card");
            logger.log("sell", parts[1] + " is not a card", player);
        } else if (!player.doesHaveCard(parts[1])) {
            System.out.println("You don't have this card !");
            logger.log("sell", "didn't own the " + parts[1], player);
        } else {
            if (player.isCardInDecks(CardsFileManager.getCardsFileManager().getCard(parts[1]))) {
                System.out.println("This card is in one of your decks !");
                logger.log("sell", parts[1] + " is in a deck", player);
            } else {
                System.out.println("You'll get " + CardsFileManager.getCardsFileManager().getCard(parts[1]).cost() +
                        " gold for selling this card");
                System.out.println("Continue?(y/n)");
                String answer = sc.nextLine();
                if (answer.equals("n")) {
                    System.out.println("Back to store");
                    logger.log("sell", "didn't accept the sell", player);
                } else if (answer.equals("y")) {
                    int gold = CardsFileManager.getCardsFileManager().getCard(parts[1]).cost();
                    player.setPlayerGolds(player.getPlayerGolds() + gold);
                    player.getAvailableCards().remove(CardsFileManager.getCardsFileManager().getCard(parts[1]));
                    System.out.println("Sell successful !");
                    logger.log("sell", parts[1] + " has been sold", player);
                    logger.log("gold", gold + " gold added to player", player);
                } else {
                    System.out.println("No sell, back to store");
                    logger.log("unknown command", "sell has been cancelled", player);
                }
            }
        }
    }

    private void storeListHandler(String[] parts) {
        if (parts[1].equals("-s")) {
            logger.log("list", "sellable cards", player);
            System.out.println("Cards you can sell :");
            for (Card card : player.getAvailableCards()) {
                if (!player.isCardInDecks(card)) {
                    System.out.println(card.name);
                }
            }
        } else if (parts[1].equals("-b")) {
            logger.log("list", "buyable cards", player);
            System.out.println("Cards you can buy :");
            for (Card card : CardsFileManager.getCardsFileManager().getCardsSet()) {
                if (!player.doesHaveCard(card.name)) {
                    System.out.println(card.name);
                }
            }
        } else {
            logger.log("list", "unknown list", player);
            System.out.println("please try again");
        }
    }
}
