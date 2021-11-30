package worldofzuul.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;
import worldofzuul.model.*;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameController implements Initializable {
    private final Parser parser = new Parser();
    Power power = new Power();
    private Item[] allItems;

    Product windmill;
    Product watermill;
    Product solarpanel;
    Inventory inventory = new Inventory();
    private Room currentRoom;

    @FXML
    private Pane pane;
    @FXML
    private Label hoverLabel;
    @FXML
    private ListView inventoryListView;
    @FXML
    private TextArea consoleTextArea;
    @FXML
    private ImageView roomBackground;

    @FXML
    private ProgressBar powerProgressBar;

    private Image dropOffImage = new Image("/Scener/Kul.png");

    @FXML
    private ImageView powerImageView;

    private Image image1 = new Image("/Scener/img.png");
    private Image image2 = new Image("/Scener/ve-omstilling169.png");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("START GAME");
        hoverLabel.setMouseTransparent(true);
        createItems();
        createRooms();
        initializeInventory();
        printWelcome();
        updatePowerBars();
    }

    private void createItems() {
        try {
            allItems = new Item[]{
                    new Material("generator", EnergyType.WIND, 200, 200, "/item_placeholder.png"), new Material("vinger", EnergyType.WIND), new Material("tårn", EnergyType.WIND),
                    new Material("turbine", EnergyType.WATER), new Material("vandrør", EnergyType.WATER), new Material("kabel", EnergyType.WATER),
                    new Material("solpanel", EnergyType.SOLAR), new Material("inverter", EnergyType.SOLAR), new Material("stativ", EnergyType.SOLAR),
                    new Product("vindmølle", EnergyType.WIND), new Product("vandmølle", EnergyType.WATER), new Product("solcelle", EnergyType.SOLAR)};
            windmill = (Product) allItems[9];
            watermill = (Product) allItems[10];
            solarpanel = (Product) allItems[11];
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    private void initializeInventory() {
        inventoryListView.setItems(inventory.getItems());
        inventoryListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {

            @Override
            public ListCell<Item> call(ListView<Item> list) {
                ListCell<Item> cell = new ListCell<Item>() {
                    @Override
                    public void updateItem(Item item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        }
                    }
                };
                return cell;
            }
        });
    }

    public void placeProduct(){
        try {
            for (Object item : inventoryListView.getItems()) {
                if (item instanceof Product) {
                    int selectedID = inventoryListView.getSelectionModel().getSelectedIndex();
                    power.addPower((Product) inventoryListView.getSelectionModel().getSelectedItem(), currentRoom);
                    inventoryListView.getItems().remove(selectedID);
                    updatePowerBars();
                    return;
                }
                System.out.println("PRODUKT IKKE REGISTRERET");
            }
        } catch (IndexOutOfBoundsException e){
            System.out.println("PRODUKT IKKE VALGT");
        }
    }

    public void updatePowerBars(){
        double powerPercentage = power.getPower()/100;
        powerProgressBar.setProgress(powerPercentage);

        /*if (powerPercentage == 0) {
            powerImageView.setImage(image2);
        } else {
            powerImageView.setImage(image1);
        }*/
    }

    private void createRooms() {
        Room start, coal, workshop, wind1, wind2, wind3, wind4, water1, water2, water3, water4, water5, solar1, solar2, solar3, solar4;
        try {

            start = new Room("i et hus med en lyskilde, der ikke lyser. Det ligner at strømkilden er mod øst", "/Scener/Start.png");
            coal = new Room("i en kælder med et kulkraftværk. Det ligner du er løbet tør for kul", "/Scener/Kul.png");
            workshop = new Room("i et værksted med tre forskellige arbejdsborde. Der er 3 døre der fører udenfor", "/Scener/Værksted.png");

            wind1 = new Room("udenfor i et område, hvor du kan mærke det blæser", "/Scener/Vind_1.png");
            wind2 = new Room("udenfor i et område, hvor der er en mild vind. Du ser nogle træer der giver læ for vinden", EnergyType.WIND, "middle", "Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da den milde vind og træerne.", "/Scener/Vind_2.png");
            wind3 = new Room("udenfor i et område, hvor det blæser. Du ser ikke noget der dækker for vinden", EnergyType.WIND, "best", "Din vindmølle genererer en rigtig god mængde energi, da det blæser og der ikke er noget som dækker.", "/Scener/Vind_3.png");
            wind4 = new Room("udenfor i et område, hvor det er en meget stærk vind", EnergyType.WIND, "worst", "Din vindmølle genererer en god mængde energi, men det er ikke optimalt, da vinden er for stærk.", "/Scener/Vind_4.png");

            water1 = new Room("udenfor i et område, hvor du ser et vandfald", EnergyType.WATER, "best", "Din vandmølle genererer en rigtig god mængde energi, da der er en masse energi fra vandet der falder.", "/Scener/Vand_1.png");
            water2 = new Room("udenfor i et område, hvor du ser en bakke du kan gå op af", "/Scener/Vand_2.png");
            water3 = new Room("oppe på bakken, hvor du ser en flod gå gennem området", EnergyType.WATER, "middle", "Din vandmølle genererer en god mængde energi, men det er ikke optimalt, da en flod ikke er hvor der er mest energi.", "/Scener/Vand_3.png");
            water4 = new Room("oppe på bakken, hvor du ser floden gå ned til vandfaldet", "/Scener/Vand_4.png");
            water5 = new Room("oppe på bakken, hvor du ser en sø, der munder ud i en flod", EnergyType.WATER, "worst", "Din vandmølle genererer lidt energi, men det er ikke optimalt, da der ikke er meget energi i stilleliggende vand.", "/Scener/Vand_5.png");

            solar1 = new Room("udenfor i et varmt område med meget sollys", "/Scener/Sol_1.png");
            solar2 = new Room("på en flad mark med meget sol", EnergyType.SOLAR, "middle", "Din solcelle genererer en god mængde energi, men det er ikke optimalt, da en solcelle helst skal ligge på skrå.", "/Scener/Sol_2.png");
            solar3 = new Room("i en skov, hvor træerne dækker for solen", EnergyType.SOLAR, "worst", "Din solcelle genererer lidt energi, men det er ikke optimalt, da træerne skygger for solen.", "/Scener/Sol_3.png");
            solar4 = new Room("i et område med en bakke, hvor der er meget sol", EnergyType.SOLAR, "best", "Din solcelle genererer en rigtig god mængde energi, da der er en masse sol og den kan ligge med ca. 45 graders skråning på bakken. ", "/Scener/Sol_4.png");

//            CraftingRoom craftingWind = new CraftingRoom("foran et grønt bord. Over bordet er der et skilt hvorpå der står \"vindenergi\".", EnergyType.WIND, windmill);
//       CraftingRoom craftingWater = new CraftingRoom("foran et blåt bord. Over bordet er der et skilt hvorpå der står \"vandenergi\".", EnergyType.WATER, watermill);
//       CraftingRoom craftingSun = new CraftingRoom("foran et gult bord. Over bordet er der et skilt hvorpå der står \"solenergi\".", EnergyType.SOLAR, solarpanel);

            //Udgange fra start
            start.setExit(new Exit(coal, 100, 200, 700, 200, "vest"));
            //start.setExit("vest", new Exit(workshop,100, 200, 0, 200));

            //Udgang fra kul
            coal.setExit(new Exit(start, 100, 200, 0, 200, "øst"));
/*
        //Udgange fra værksted
        workshop.setExit("nord", wind1);
        workshop.setExit("vest", water1);
        workshop.setExit("syd", solar1);
        workshop.setExit("vindstation", craftingWind);
        workshop.setExit("solstation", craftingSun);
        workshop.setExit("vandstation", craftingWater);
        workshop.setExit("øst", start);

        craftingWind.setExit("ud", workshop);
        craftingSun.setExit("ud", workshop);
        craftingWater.setExit("ud", workshop);

        //Udgange fra vind1
        wind1.setExit("øst", wind2);
        wind1.setExit("nord", wind3);
        wind1.setExit("syd", workshop);

        //Udgang fra vind2
        wind2.setExit("vest", wind1);

        //Udgange fra vind3
        wind3.setExit("syd", wind1);
        wind3.setExit("vest", wind4);

        //Udgang fra vind4
        wind4.setExit("øst", wind3);

        //Udgange fra vand1
        water1.setExit("øst", workshop);
        water1.setExit("nord", water2);

        //Udgange fra vand2
        water2.setExit("vest", water3);
        water2.setExit("syd", water1);

        //udgange fra vand3
        water3.setExit("øst", water2);
        water3.setExit("syd", water4);
        water3.setExit("nord", water5);

        //Udgang fra vand4
        water4.setExit("nord", water3);

        //Udgang fra vand5
        water5.setExit("syd", water3);

        //Udgange fra sol1
        solar1.setExit("nord", workshop);
        solar1.setExit("vest", solar2);
        solar1.setExit("syd", solar3);

        //Udgang fra sol2
        solar2.setExit("øst", solar1);

        //Udgange fra sol3
        solar3.setExit("nord", solar1);
       solar3.setExit("vest", solar4);

        //Udgang fra sol4
        solar4.setExit("øst", solar3);
*/
            goRoom(start);
            coal.addItem(allItems[0]);
//
//        //vind items placering i rum
//        wind1.addItem(allItems[0]);
//        wind2.addItem(allItems[1]);
//        wind4.addItem(allItems[2]);
//
//        //Vand items placering i rum
//        water2.addItem(allItems[3]);
//        water3.addItem(allItems[4]);
//        water4.addItem(allItems[5]);
//
//        //Sol items placering i rum
//        solar1.addItem(allItems[6]);
//        solar2.addItem(allItems[7]);
//        solar3.addItem(allItems[8]);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void print(String text) {
        consoleTextArea.setText(text + '\n');
    }

    private void printWelcome() {
        print("Velkommen til spillet for bæredygtig energi!\nDette er et spil som vil lære dig om forskellige energiløsninger.");
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        if (commandWord == CommandWord.UNKNOWN) {
            System.out.println("Jeg er ikke helt med på hvad du mener...");
            return false;
        }

        if (commandWord == CommandWord.HELP) {
            printHelp();
        } else if (commandWord == CommandWord.QUIT) {
            wantToQuit = quit(command);
        } else if (commandWord == CommandWord.PLACE) {
            placeOnDropOff(command);
        } else if (commandWord == CommandWord.CRAFT) {
            craft(command);
        } else if (commandWord == CommandWord.POWER) {
            printPower();
        }

        return wantToQuit;

    }

    private void pickupItem(Item newItem) {
        if (newItem.getWeight() + inventory.getCurrentWeight() > inventory.getWeightLimit()) {
            print("Du har ikke plads til at samle denne ting op");
            return;
        }
        inventory.addItem(newItem);
        currentRoom.removeItem(newItem);
        print("Du har samlet " + newItem.getName() + " op");
        if (newItem instanceof Product) {
            power.removePower((Product) newItem, currentRoom);
        }
    }

    private void printHelp() {
        System.out.println("Tak fordi du spørger om hjælp");
        System.out.println();
        System.out.println("Dine muligheder er følgende:");
        parser.showCommands();
    }

    private void goRoom(Room nextRoom) {
        if (nextRoom == null) {
            System.out.println("Det er ikke muligt!");
        } else {


            this.currentRoom = nextRoom;

            this.roomBackground.setImage(nextRoom.getBackgroundImage());
            initializeExits(nextRoom);
            initializeItems(nextRoom);

            // If the room you're entering is a CraftingRoom, check the energyType and take any materials of that type.
            if (currentRoom instanceof CraftingRoom craftingRoom) {

                // Vi bruger en iterator for at undgå en ConcurrentModificationException
                for (Iterator<Item> iterator = inventory.getItems().iterator(); iterator.hasNext(); ) {
                    Item item = iterator.next();
                    if (item.getEnergyType() == craftingRoom.getEnergyType() && item instanceof Material materialItem) {
                        System.out.println("Du får en lys idé og lægger din/dit " + item.getName() + " på arbejdsbordet!");
                        craftingRoom.placeItem(materialItem);
                        iterator.remove();
                    }
                }
            }
        }
    }

    private void initializeExits(Room nextRoom) {
        // Clear existing exits before adding new ones.
        pane.getChildren().removeIf(it -> it instanceof Exit);


        ArrayList<Exit> exits = nextRoom.getExits();

        pane.getChildren().addAll(exits);
        for (Exit exit : exits) {
            exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    goRoom(exit.getRoom());
                }
            });

            exit.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    this.hoverLabel.setTranslateX(exit.getX() + 5);
                    this.hoverLabel.setTranslateY(exit.getY() + exit.getHeight() / 2);
                    this.hoverLabel.setVisible(true);
                    this.hoverLabel.setText("Gå " + exit.getDirection());
                    System.out.println("HOVER");
                } else {
                    this.hoverLabel.setVisible(false);
                    System.out.println("NOT HOVER");
                }
            });
        }
    }

    private void initializeItems(Room nextRoom) {
        // Clear existing items before loading in new ones
        pane.getChildren().removeIf(it -> it instanceof Item);

        ArrayList<Item> items = nextRoom.getItems();
        System.out.print(items);
        pane.getChildren().addAll(items);
        for (Item item : items) {
            item.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Item item = (Item) mouseEvent.getSource();
                    pickupItem(item);
                }
            });

            item.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    this.hoverLabel.setTranslateX(item.getX());
                    this.hoverLabel.setTranslateY(item.getY());
                    this.hoverLabel.setVisible(true);
                    hoverLabel.setText(item.getName());
                    hoverLabel.toFront();
                    System.out.println("HOVER");
                } else {
                    this.hoverLabel.setVisible(false);
                    System.out.println("NOT HOVER");
                }
            });
        }
    }

    private void craft(Command command) {
        if (!(currentRoom instanceof CraftingRoom craftingRoom)) {
            System.out.println("Du kan ikke bygge noget her!");
            return;
        }
        if (craftingRoom.canCraft()) {
            System.out.println("Du bygger en " + craftingRoom.getCraftingResult().getName());
            inventory.addItem(craftingRoom.getCraftingResult());
            return;
        }
        System.out.println("Du mangler noget før du kan bygge en " + craftingRoom.getCraftingResult().getName());
        System.out.println("Arbejdsbordet indeholder følgende: " + craftingRoom.getPlacedItemsString());
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Giver du op?");
            return false;
        } else {
            return true;
        }
    }

    private void placeOnDropOff(Command command) {
        if (currentRoom.getDropOffEffect() != null) {
            if (!command.hasSecondWord()) {
                System.out.println("Hvad skal jeg sætte?");
                return;
            }
            String productName = command.getSecondWord();
            Item product = null;

            for (int i = 0; i < allItems.length; i++) {
                if (allItems[i].getName().equals(productName)) {
                    product = allItems[i];
                }
            }
            if (product == null) {
                System.out.println("Genstanden blev ikke genkendt");
            } else if (product instanceof Material) {
                System.out.println("Du kan ikke sætte materialer, kun produkter");
            } else if (!inventory.getItems().contains(product)) {
                System.out.println("Du har ikke det nævnte produkt i dit inventar");
            } else {
                inventory.removeItem(product);
                currentRoom.addItem(product);
                System.out.println("Du har sat " + product.getName() + " i det nuværende rum");
                power.addPower((Product) product, currentRoom);
            }
        } else {
            System.out.println("Der er ikke noget sted at placere produkter i dette rum");
        }
    }

    public void printPower() {
        System.out.println("Du har " + power.getPower() + "% strøm");
    }
}