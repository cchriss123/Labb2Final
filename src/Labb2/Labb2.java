package Labb2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Labb2 {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Product> cart = new ArrayList<>();
        Discounter discounter = Discounter.noDiscount();
        Gson gson = new Gson();


        String homeFolder = System.getProperty("user.home");
        final var path = Path.of(homeFolder, "products.json");
        //final var path = Path.of("src/products.json");
        if (Files.exists(path)) {
            products = gson.fromJson(readFile(path), new TypeToken<ArrayList<Product>>() {
            }.getType());
        }
        else {
            products.add(new Product("Gaming", "PG279QM", "Asus",
                new BigDecimal(5999), 27, 223, 1000));
            products.add(new Product("Gaming", "PG259QM", "Asus",
                new BigDecimal(2999), 25, 229, 1001));
            products.add(new Product("Gaming", "VG270U", "Acer",
                new BigDecimal(2499), 27, 333, 1002));
            products.add(new Product("Gaming", "VG220Q", "Acer",
                new BigDecimal(1299), 24, 488, 1003));
            products.add(new Product("Gaming", "G241MYS", "MSI",
                new BigDecimal(1999), 24, 377, 1004));

            products.add(new Product("Office", "VA247HE", "Asus",
                new BigDecimal(1799), 24, 229, 2000));
            products.add(new Product("Office", "BL2785TC", "BenQ",
                new BigDecimal(2999), 25, 224, 2001));
            products.add(new Product("Office", "U2722DE", "Dell",
                new BigDecimal(7999), 27, 99, 2002));
            products.add(new Product("Office", "KA242Y", "Acer",
                new BigDecimal(1299), 24, 488, 2003));

            products.add(new Product("TV", "65C725N", "TCL",
                new BigDecimal(9999), 65, 199, 3000));
            products.add(new Product("TV", "QLED760", "TCL",
                new BigDecimal(5999), 55, 233, 3001));
            products.add(new Product("TV", "48C2", "LG",
                new BigDecimal(1999), 48, 234, 3002));
            products.add(new Product("TV", "55C2", "LG",
                new BigDecimal(12999), 55, 899, 3003));
            products.add(new Product("TV", "54C2", "LG",
                new BigDecimal(19999), 65, 992, 3004));
           }

        mainMenu(sc, products, cart, discounter, gson, path);
    }

    private static String readFile(Path path) {

        String fileContent = "";
        try {
            fileContent = Files.readString(path);
        }
        catch (FileAlreadyExistsException e) {
            System.out.println("File already exists: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getClass().getName() + " " + e.getMessage());
        }
        return fileContent;
    }

    private static void mainMenu(Scanner sc, ArrayList<Product> products, ArrayList<Product> cart, Discounter discounter, Gson gson, Path path) {

        boolean isShowMenu = true;
        while(isShowMenu){

            String menuText = """
                    Välkommen till Christofers Skärmshop! Din monitor och TV grossist!
                    ==================================================================
                    Väl något an följande alternativ.
                    Välkommen till Christofers Monitorer! Din Monitor och TV grossist!
                    1. Sök bland produkter
                    2. Ändra i produktsortimentet
                    3. Kund
                    4. Spara
                    
                    e. Avsluta
                    """;

            System.out.println(menuText);

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> searchMenu(products, sc);
                case "2" -> sellerMenu(products, sc);
                case "3" -> customerMenu(products, cart, sc, discounter);
                case "4" -> saveToFile(products, gson, path);

                case "e", "E" -> {
                    System.out.println("Avslutar");
                    isShowMenu = false;
                }
                default -> System.out.println("Felaktigt val");
            }
        }
    }

    private static void customerMenu(ArrayList<Product> products, ArrayList<Product> cart, Scanner sc, Discounter discounter) {

        boolean isShowMenu = true;
        while (isShowMenu) {

            String menuText = """
                     1. Handla
                     2. Skriv in rabattkod
                     3. Kvitto
                     
                     e. Gå tillbaka till huvudmeny.
                    """;
            System.out.println(menuText);

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> shop(products, cart, sc);
                case "2" -> discounter = enterDiscountCode(sc);
                case "3" -> receipt(cart, sc , discounter);

                case "e", "E" -> {
                    isShowMenu = false;
                    System.out.println("Avslutar");
                }
                default -> {
                    System.out.println("Felaktigt val");
                    pressEnter(sc);
                }
            }
        }
    }

    private static void sellerMenu(ArrayList<Product> products, Scanner sc) {

        boolean isShowMenu = true;
        while(isShowMenu){

            String menuText = """
                    1. Lägg till produkt
                    2. Ta bort produkt
                    3. Ändra lagersaldo på produkt
                    
                    e. Gå tillbaka till huvudmeny.
                   """;
            System.out.println(menuText);

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> addProduct(products, sc);
                case "2" -> removeProduct(products, sc);
                case "3" -> changeInventoryBalance(products, sc);

                case "e", "E" -> {
                    System.out.println("Avslutar");
                    isShowMenu = false;
                }
                default -> System.out.println("Felaktigt val");
            }
        }
    }

    private static void removeProduct(ArrayList<Product> products, Scanner sc) {

        String chooseCategory = categoryOptions(sc);
        products.stream()
                .filter(product -> product.getCategory().equals(chooseCategory))
                .forEach(product -> System.out.println(product.getEanCode() + "--> " + product));
        int eonComparator;
        System.out.println("Skriv in eanCode på den produkt du vill radera.");
        eonComparator = parseInput(sc);

        if (isEanCodeInList(products, eonComparator)) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getEanCode() == eonComparator){
                    products.remove(i);
                    break;
                }
            }
        }
        else System.out.println("Produkten finns inte.");
    }

    private static void receipt(ArrayList<Product> cart, Scanner sc, Discounter discounter) {
        BigDecimal sum = cart
                .stream()
                .map(Product::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.forEach(System.out::println);
        System.out.println("Totalkostnad för produkterna är " +  sum + ".");
        System.out.println("Att betala: " + discounter.applyDiscount(sum));
        pressEnter(sc);
    }

    private static void shop(ArrayList<Product> products, ArrayList<Product> cart, Scanner sc) {

        String chooseCategory = categoryOptions(sc);
        products.stream()
                .filter(product -> product.getCategory().equals(chooseCategory))
                .forEach(product -> System.out.println(product.getEanCode() + "--> " + product));

        int eonComparator;
        System.out.println("Skriv in eanCode på den produkten du vill köpa.");
        eonComparator = parseInput(sc);

        if (isEanCodeInList(products, eonComparator)) {
            for (Product product : products) {
                if (product.getEanCode() == eonComparator) {
                    product.setQuantity(product.getQuantity() - 1);
                    cart.add(product);
                    for (int j = 0; j < cart.size(); j++)
                        cart.get(cart.size() - 1).setQuantity(1);
                }
            }
        }
        else
            System.out.println("Produkten finns inte.");

        System.out.println("1. För att lägga till en till produkt i kundvagnen");
        System.out.println("Tryck på enter ENTER för att gå tillbaka till menyn");

        String input = sc.nextLine();
        if (input.equals("1")) {
            shop(products, cart, sc);
        }
    }

    private static void changeInventoryBalance(ArrayList<Product> products, Scanner sc) {

        String chooseCategory = categoryOptions(sc);
        products.stream()
                .filter(product -> product.getCategory().equals(chooseCategory))
                .forEach(product -> System.out.println(product.getEanCode() + "--> " + product));
        int eonComparator;
        System.out.println("Skriv in eanCode på den produkt du vill ändra lagersaldot på.");

        eonComparator = parseInput(sc);
        if (isEanCodeInList(products, eonComparator)) {
            System.out.println("Skriv in nya lagersaldot.");
            int balanceSetter = parseInput(sc);
            for (Product product : products) {
                if (product.getEanCode() == eonComparator)
                    product.setQuantity(balanceSetter);
            }
        }
        else System.out.println("Produkten finns inte.");
    }

    private static void addProduct(ArrayList<Product> products, Scanner sc) {

        int eanCode;
        String category = categoryOptions(sc);
        System.out.println("Skriv in produktnamn");
        String name = sc.nextLine();
        System.out.println("Skriv in produktens märke.");
        String brand = sc.nextLine();
        System.out.println("Skriv in produktens pris.");
        BigDecimal price = BigDecimal.valueOf(parseInput(sc));
        System.out.println("Skriv in produktens storlek i tum.");
        int size = parseInput(sc);
        System.out.println("Skriv in produktens lagersaldo.");
        int inventoryBalance = parseInput(sc);

        switch (category) {
            case "Gaming" -> eanCode = eanCodeAssigner(products, 1000);
            case "Office" -> eanCode = eanCodeAssigner(products, 2000);
            case "TV" -> eanCode = eanCodeAssigner(products, 3000);
            default -> eanCode = eanCodeAssigner(products, 4000);
        }
        products.add(new Product(category, name, brand, price, size, inventoryBalance, eanCode));
    }

    private static int parseInput(Scanner sc) {
        while (true){
            try {
                return Integer.parseInt(sc.nextLine());
            }
            catch (Exception e) {
                System.out.println("Var vänlig skriv in ett heltal.");
            }
        }
    }

    private static int eanCodeAssigner(ArrayList<Product> products, int eanCode) {
        while (isEanCodeInList(products, eanCode))
            eanCode++;
        return eanCode;
    }

    private static boolean isEanCodeInList(ArrayList<Product> products, int eanCode) {
        for (Product product : products) {
            if (product.getEanCode() == eanCode)
                return true;
        }
        return false;
    }

    private static void saveToFile(ArrayList<Product> products, Gson gson, Path path) {

        String json = gson.toJson(products);

        try {
            Files.writeString(Path.of(String.valueOf(path)), json);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void pressEnter(Scanner sc) {
        System.out.println("\nPress \"ENTER\" to continue...");
        sc.nextLine();
    }

    private static void searchMenu(ArrayList<Product> products, Scanner sc) {
        boolean isShowMenu = true;
        while (isShowMenu) {

            String menuText = """
                    1. Skriv ut produktkategori
                    2. Sök efter prisintervall och sortera efter pris
                    3. Sök efter skärmstorlek
                    
                    e. Gå tillbaka till huvudmeny.
                   """;
            System.out.println(menuText);

            String choice = sc.nextLine();
            switch (choice) {
                case "1" -> {
                    String chooseCategory = categoryOptions(sc);
                    products.stream()
                            .filter(product -> product.getCategory().equals(chooseCategory))
                            .forEach(System.out::println);
                    pressEnter(sc);
                }
                case "2" -> searchPriceInterval(products, sc);
                case "3" -> searchSize(products, sc);

                case "e", "E" -> {
                    isShowMenu = false;
                    System.out.println("Avslutar");
                }
                default -> {
                    System.out.println("Felaktigt val");
                    pressEnter(sc);
                }
            }
        }
    }

    private static void searchSize(ArrayList<Product> products, Scanner sc) {

        System.out.println("Skriv in minsta bildskärmstorleken inom sökintervallet.");
        int minSize = parseInput(sc);
        System.out.println("Skriv in största bildskärmstorleken inom sökintervallet.");
        int maxSize = parseInput(sc);
        final int finalMinSize = minSize;
        final int finalMaxSize = maxSize;

        System.out.println("Antal träffar: " + products.stream()
                .filter(product -> product.getSize() >= finalMinSize)
                .filter(product -> product.getSize() <= finalMaxSize)
                .count());

        products.stream()
                .filter(product -> product.getSize() >= finalMinSize)
                .filter(product -> product.getSize() <= finalMaxSize)
                .sorted(Comparator.comparing(product -> product.getSize()))
                .forEach(System.out::println);
        pressEnter(sc);
    }

    private static void searchPriceInterval(ArrayList<Product> products, Scanner sc) {

        System.out.println("Skriv in lägsta priset inom sökintervallet.");
        int minPrice = parseInput(sc);
        System.out.println("Skriv in högsta bildskärmstorleken inom sökintervallet.");
        int maxPrice = parseInput(sc);
        final BigDecimal finalMinPrice = new BigDecimal(minPrice);
        final BigDecimal finalMaxPrice = new BigDecimal(maxPrice);

        System.out.println("Antal träffar: " + products.stream()
                .filter(product -> product.getPrice().compareTo(finalMinPrice) >= 0)
                .filter(product -> product.getPrice().compareTo(finalMaxPrice) <= 0)
                .count());

        products.stream()
                .filter(product -> product.getPrice().compareTo(finalMinPrice) >= 0)
                .filter(product -> product.getPrice().compareTo(finalMaxPrice) <= 0)
                .sorted(Comparator.comparing(product -> product.getPrice()))
                .forEach(System.out::println);
        pressEnter(sc);
    }

    private static String categoryOptions(Scanner sc) {

        System.out.println("Välj något av följande alternativ\n1: Gaming \n2: Office \n3 TV");
        String choice = sc.nextLine();
        return switch (choice) {
            case "1" -> "Gaming";
            case "2" -> "Office";
            case "3" -> "TV";
            default -> categoryOptions(sc);
            };
    }

    private static Discounter enterDiscountCode(Scanner sc) {
        System.out.println("Skriv in rabattkod.(easter/christmas)");
        String discountCode = sc.nextLine();
        return switch (discountCode) {
            case "easter" -> Discounter.easterDiscounter();
            case "christmas" -> Discounter.christmasDiscounter();
            default -> Discounter.noDiscount();
        };
    }
}


