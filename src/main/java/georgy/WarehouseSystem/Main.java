package georgy.WarehouseSystem;


import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        List<String> log_data = JsonRedactor.getDBLogin();
        assert log_data != null;
        String login, password;
        SQlConnector sQlConnector = new SQlConnector(Objects.requireNonNull(log_data).get(0), log_data.get(1));
        if (args.length != 0 && Arrays.asList(args).contains("-c")) {
            System.out.print("Please Enter your login: ");
            Scanner scanner = new Scanner(System.in);
            login = scanner.nextLine();
            System.out.print("Please Enter your password: ");
            password = scanner.nextLine();
        } else {
            //Work in progress graphics should be here but i have no time
            login = null;
            password = null;
        }
        assert password != null;
        if (LoginSystem.login(sQlConnector, login, password)) {
            System.out.println("Success");
            sQlConnector.setLogin(login);
            sQlConnector.setCurrentUserPrivileges();
            //      if (args.length != 1 && Arrays.asList(args).contains("-c")) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("command: ");
                String command = scanner.next();
                //FORGIVE ME FOR THIS BUT IT'S NEEDED
                if (command.equals("exit")) {
                    return;
                }
                if (command.equals("showOrder")) {
                    System.out.print("Order ID: ");
                    int ID = scanner.nextInt();
                    sQlConnector.showOrderInfo(ID);
                }
                if (command.equals("showAllOrders")) {
                    sQlConnector.showAllOrders();
                }
                if (command.equals("showAssignedTasks")) {
                    System.out.print("UserID ");
                    Integer ID = scanner.nextInt();
                    sQlConnector.showAssignedOrders(ID);
                }
                if (command.equals("showWorkers")) {
                    System.out.print("OrderID ");
                    Integer ID = scanner.nextInt();
                    sQlConnector.showAffiliatedWorkers(ID);
                }
                if (command.equals("getUserID")) {
                    System.out.print("User Login: ");
                    String user_login = scanner.next();
                    System.out.println("User ID: " + sQlConnector.getUserId(user_login));
                }
                if (command.equals("getAllUsers")) {
                    sQlConnector.getAllUsers();
                }
                if (command.equals("getItemID")) {
                    System.out.print("Write item name: ");
                    String name = scanner.next();
                    System.out.println("Item ID: " + sQlConnector.getItemID(name));
                }
                if (command.equals("getAllItems")) {
                    sQlConnector.getAllItems();
                }
                if (command.equals("getItemInfo")) {
                    System.out.print("Item ID: ");
                    Integer ID = scanner.nextInt();
                    sQlConnector.getItemInfo(ID);
                }
                if (command.equals("addItem")) {
                    if (sQlConnector.getPosition().equals("admin") || sQlConnector.getPosition().equals("controller")) {
                        System.out.print("Write item name: ");
                        String name = scanner.next();
                        System.out.print("Write item Description: ");
                        String description = scanner.next();
                        sQlConnector.addItem(name, description);
                    } else {
                        System.out.println("Operation is not allowed");
                    }
                }
                if (command.equals("addWorkerToOrder")) {
                    if (sQlConnector.getPosition().equals("admin") || sQlConnector.getPosition().equals("controller")) {
                        System.out.print("OrderID: ");
                        Integer OrderID = scanner.nextInt();
                        System.out.println("UserID: ");
                        Integer UserID = scanner.nextInt();
                        sQlConnector.assignToOrder(OrderID, UserID);
                    } else {
                        System.out.println("Operation is not allowed");
                    }
                }
                if (command.equals("removeWorkerFromOrder")) {
                    if (sQlConnector.getPosition().equals("admin") || sQlConnector.getPosition().equals("controller")) {
                        System.out.print("OrderID: ");
                        Integer OrderID = scanner.nextInt();
                        System.out.println("UserID: ");
                        Integer UserID = scanner.nextInt();
                        sQlConnector.removeFromOrder(OrderID, UserID);
                    } else {
                        System.out.println("Operation is not allowed");
                    }
                }
                if (command.equals("addOrder")) {
                    if (sQlConnector.getPosition().equals("admin") || sQlConnector.getPosition().equals("controller")) {

                        System.out.print("True if delivery, False if production: ");
                        Boolean type = scanner.nextBoolean();
                        System.out.print("Set initial status: ");
                        String status = scanner.next();
                        System.out.print("Write Associated workers ID's (please write them with space between): ");
                        String workers = scanner.next();
                        workers += scanner.nextLine();
                        String[] worker_list = workers.split(" ");
                        System.out.print("Please Write ID's of Associated Items(please write them with space between): ");
                        String items = scanner.next();
                        items += scanner.nextLine();
                        String[] items_list = items.split(" ");
                        System.out.print("Please write the amount of each item(please write them with space between): ");
                        String amounts = scanner.next();
                        amounts += scanner.nextLine();
                        String[] amounts_list = amounts.split(" ");
                        while (amounts_list.length != items_list.length) {
                            System.out.print("Please write the amount of each item(please write them with space between): ");
                            amounts = scanner.next();
                            amounts += scanner.nextLine();
                            amounts_list = amounts.split(" ");
                        }

                        sQlConnector.createOrder(type, status, worker_list, items_list, amounts_list);
                    } else {
                        System.out.println("Operation is not allowed");
                    }
                }
                if (command.equals("addUser")) {
                    if (sQlConnector.getPosition().equals("admin")) {
                        LinkedList<String> creationString = new LinkedList<>();
                        System.out.print("Write user name: ");
                        creationString.add(scanner.next());
                        System.out.print("Write user surname: ");
                        creationString.add(scanner.next());
                        System.out.print("Write user login: ");
                        creationString.add(scanner.next());
                        System.out.print("Write user password: ");
                        creationString.add(org.apache.commons.codec.digest.DigestUtils.md5Hex(LoginSystem.crypt(scanner.next())));
                        System.out.print("Write Position: ");
                        creationString.add(scanner.next());
                        sQlConnector.createNewUser(creationString);
                    } else {
                        System.out.println("Operation is not allowed");
                    }
                } else if (command.equals("help")) {
                    System.out.println("exit, addUser <name> <password>, removeUser <name>, assignOrder <OrderId>, getOrderDetails <OrderId>, createOrder, deleteOrder,");
                }
                System.out.println();
                //      }
            }
        }
    }
}
