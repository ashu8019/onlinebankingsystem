package code.vibes.onlinebankingsystem;

import java.util.Scanner;

import code.vibes.onlinebankingsystem.dao.AccountantDao;
import code.vibes.onlinebankingsystem.dao.AccountantDaoImplementation;
import code.vibes.onlinebankingsystem.dao.CustomerDao;
import code.vibes.onlinebankingsystem.dao.CustomerDaoImplementation;
import code.vibes.onlinebankingsystem.entity.Accountant;
import code.vibes.onlinebankingsystem.entity.Customer;
import code.vibes.onlinebankingsystem.exception.AccountantException;
import code.vibes.onlinebankingsystem.exception.CustomerException;

public class App {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean mainLoop = true;

        while (mainLoop) {

            System.out.println("\n=========== WELCOME TO ONLINE BANKING SYSTEM ===========");
            System.out.println("1. ADMIN LOGIN PORTAL");
            System.out.println("2. CUSTOMER LOGIN");
            System.out.println("3. EXIT");
            System.out.print("Enter Choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // Clear buffer

            switch (choice) {

                // ================= ADMIN LOGIN =================
                case 1:

                    System.out.print("Enter Username: ");
                    String username = sc.nextLine();

                    System.out.print("Enter Password: ");
                    String pass = sc.nextLine();

                    AccountantDao ad = new AccountantDaoImplementation();

                    try {

                        Accountant a = ad.loginAccountant(username, pass);
                        System.out.println("\nLogin Successful!");
                        System.out.println("Welcome Admin: " + a.getAccountantUsername());

                        boolean adminMenu = true;

                        while (adminMenu) {

                            System.out.println("\n------------- ADMIN MENU -------------");
                            System.out.println("1. Add Customer");
                            System.out.println("2. Add Account");
                            System.out.println("3. Update Customer Address");
                            System.out.println("4. Delete Account");
                            System.out.println("5. View All Customers");
                            System.out.println("6. Logout");
                            System.out.print("Enter Choice: ");

                            int adminChoice = sc.nextInt();
                            sc.nextLine();

                            switch (adminChoice) {

                                case 1:
                                    System.out.print("Enter Customer Name: ");
                                    String name = sc.nextLine();

                                    System.out.print("Enter Email: ");
                                    String mail = sc.nextLine();

                                    System.out.print("Enter Password: ");
                                    String password = sc.nextLine();

                                    System.out.print("Enter Mobile: ");
                                    String mobile = sc.nextLine();

                                    System.out.print("Enter Address: ");
                                    String address = sc.nextLine();

                                    int cid = ad.addCustomer(name, mail, password, mobile, address);
                                    System.out.println("Customer Added Successfully! CID: " + cid);
                                    break;

                                case 2:
                                    System.out.print("Enter Initial Balance: ");
                                    int balance = sc.nextInt();

                                    System.out.print("Enter Customer CID: ");
                                    int customerCid = sc.nextInt();
                                    sc.nextLine();

                                    String result = ad.addAccount(balance, customerCid);
                                    System.out.println(result);
                                    break;

                                case 3:
                                    System.out.print("Enter Account Number: ");
                                    int accNo = sc.nextInt();
                                    sc.nextLine();

                                    System.out.print("Enter New Address: ");
                                    String newAddress = sc.nextLine();

                                    System.out.println(ad.updateCustomer(accNo, newAddress));
                                    break;

                                case 4:
                                    System.out.print("Enter Account Number to Delete: ");
                                    int deleteAcc = sc.nextInt();
                                    sc.nextLine();

                                    System.out.println(ad.deleteAccount(deleteAcc));
                                    break;

                                case 5:
                                    ad.viewAllCustomer();
                                    break;

                                case 6:
                                    adminMenu = false;
                                    System.out.println("Admin Logged Out Successfully");
                                    break;

                                default:
                                    System.out.println("Invalid Option!");
                            }
                        }

                    } catch (AccountantException | CustomerException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                // ================= CUSTOMER LOGIN =================
                case 2:

                    System.out.println("\n----------- CUSTOMER LOGIN -----------");

                    System.out.print("Enter Name: ");
                    String customerUsername = sc.nextLine();

                    System.out.print("Enter Password: ");
                    String customerPassword = sc.nextLine();

                    System.out.print("Enter Account Number: ");
                    int accountNumber = sc.nextInt();
                    sc.nextLine();

                    CustomerDao cd = new CustomerDaoImplementation();

                    try {

                        Customer cus = cd.loginCustomer(customerUsername, customerPassword, accountNumber);
                        System.out.println("Welcome " + cus.getCustomerName());

                        boolean customerMenu = true;

                        while (customerMenu) {

                            System.out.println("\n1. View Balance");
                            System.out.println("2. Deposit Money");
                            System.out.println("3. Withdraw Money");
                            System.out.println("4. Transfer Money");
                            System.out.println("5. Logout");
                            System.out.print("Enter Choice: ");

                            int x = sc.nextInt();
                            sc.nextLine();

                            switch (x) {

                                case 1:
                                    System.out.println("Current Balance: "
                                            + cd.viewBalance(accountNumber));
                                    break;

                                case 2:
                                    System.out.print("Enter Amount to Deposit: ");
                                    int depositAmount = sc.nextInt();
                                    sc.nextLine();

                                    cd.Deposit(accountNumber, depositAmount);
                                    System.out.println("Updated Balance: "
                                            + cd.viewBalance(accountNumber));
                                    break;

                                case 3:
                                    System.out.print("Enter Withdrawal Amount: ");
                                    int withdrawAmount = sc.nextInt();
                                    sc.nextLine();

                                    cd.withdraw(accountNumber, withdrawAmount);
                                    System.out.println("Updated Balance: "
                                            + cd.viewBalance(accountNumber));
                                    break;

                                case 4:
                                    System.out.print("Enter Amount to Transfer: ");
                                    int amount = sc.nextInt();

                                    System.out.print("Enter Receiver Account Number: ");
                                    int receiverAcc = sc.nextInt();
                                    sc.nextLine();

                                    cd.Transfer(accountNumber, amount, receiverAcc);
                                    System.out.println("Amount Transferred Successfully!");
                                    break;

                                case 5:
                                    customerMenu = false;
                                    System.out.println("Customer Logged Out Successfully");
                                    break;

                                default:
                                    System.out.println("Invalid Option!");
                            }
                        }

                    } catch (CustomerException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    break;

                case 3:
                    System.out.println("Thank You for Using Online Banking System!");
                    mainLoop = false;
                    break;

                default:
                    System.out.println("Invalid Option!");
            }
        }

        sc.close();
    }
}
