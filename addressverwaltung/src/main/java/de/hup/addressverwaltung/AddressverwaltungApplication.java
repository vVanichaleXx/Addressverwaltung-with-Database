package de.hup.addressverwaltung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

@SpringBootApplication
public class AddressverwaltungApplication {
    public int id;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public static void main(String[] args) {
        SpringApplication.run(AddressverwaltungApplication.class, args);
        new AddressverwaltungApplication().cli();
    }

    public void cli() {
        try (Database database = new Database()) {
            CLI cli = new CLI();
            run(cli, database);
        } catch (Exception exception) {
            logger.severe(exception.getMessage());
        }
        System.out.println("Thanks for using Addressverwaltung");
    }

    private void run(CLI cli, Database database) throws Exception {
        var shouldExit = false;
        while (!shouldExit) {
            var state = cli.menu();

            switch (state) {
                case LIST -> listAddresses(database);
                case ADDNEWADDRESS -> addAddress(cli, database);
                case ADD_PERSON -> addPerson(cli, database);
                case EXIT -> {
                    database.close();
                    shouldExit = true;
                }
                case KILL -> deleteAddress(cli, database);
                case EDIT -> {
                    edit_starts(cli, database);
                    edit_menu(cli, database);
                }
                case DETECT -> detectPerson(cli, database);
            }
        }
    }

    private void edit_menu(CLI cli, Database database) throws Exception {
        do {
            switch (CLI.askToDo()) {
                case 1:
                    editAddress(cli, database);
                    break;
                case 2:
                    edit_starts(cli, database);
                    break;
                case 3:
                    run(cli, database);
                    break;
                default:
                    continue;
            }
        } while (false);
    }

    private void edit_starts(CLI cli, Database database) throws SQLException {
        id = cli.promptId();
        while(editAddress(cli, database)){
            editAddress(cli, database);
        }
    }

    private boolean editAddress(CLI cli, Database database) throws SQLException {
        switch (cli.editUserMenu()) {
            case "street":
                AtomicReference<String> street = new AtomicReference<>();
                oh__blyat(() -> {
                    street.set(cli.promptStreet());
                    AddressValidator.isValidStreet(street.get());
                });

                database.updateAddressStreet(id, street.get());
                System.out.println("you succesfull changed street");
                return false;
            case "postfach":
                AtomicInteger postfach = new AtomicInteger();
                oh__blyat(() -> {
                    postfach.set(cli.promptPostfach());
                    AddressValidator.isValidPostfach(postfach.get());
                });

                database.updateAddressPostfach(id, postfach.get());
                System.out.println("you succesfull changed postfach");
                return false;
            case "plz":
                AtomicInteger plz = new AtomicInteger();
                oh__blyat(() -> {
                    plz.set(cli.promptPlz());
                    AddressValidator.isValidPostfach(plz.get());
                });
                database.updateAddressPlz(id, plz.get());
                System.out.println("you succesfull changed street");
                return false;

            case "stadt":
                AtomicReference<String> ort = new AtomicReference<>();
                oh__blyat(() -> {
                    ort.set(cli.promptOrt());
                    AddressValidator.isValidOrt(ort.get());
                });

                database.updateAddressOrt(id, ort.get());
                System.out.println("you successfully changed street");
                return false;
            default:
                System.out.println("something went wrong");
        }
        return false;
    }

    private static void detectPerson(CLI cli, Database database) {
        String name = cli.promptPersonName();
        int addressId = cli.promptId();
        try {
            database.savePerson(name, addressId);

        } catch(SQLException e) {
            System.out.println("don't worry, ivan integer will fix it ;)");
        }
    }

    private void addAddress(CLI cli, Database database) {
        AtomicReference<String> street = new AtomicReference<>();
        AtomicInteger postfach = new AtomicInteger();
        AtomicInteger plz = new AtomicInteger();
        AtomicReference<String> ort = new AtomicReference<>();

        oh__blyat(() -> {
            street.set(cli.promptStreet());
            AddressValidator.isValidStreet(street.get());
        });

        oh__blyat(() -> {
            postfach.set(cli.promptPostfach());
            AddressValidator.isValidPostfach(postfach.get());
        });

        oh__blyat(() -> {
            plz.set(cli.promptPlz());
            AddressValidator.isValidPlz(plz.get());
        });

        oh__blyat(() -> {
            ort.set(cli.promptOrt());
            AddressValidator.isValidOrt(ort.get());
        });

        try {
            //TODO: next ID?
            Address address = new Address(0,street.get(),
                postfach.get(),
                plz.get(),
                ort.get() );
            database.saveAddress(address);

            System.out.println("successfully added address:\n" + "DOWN WILL BE UR ID, DONT MISS THIS ONE, CUZ IF U WILL NEED TO CHANGE SMTH U NEED USE ID" +
                "\n" + "str: "+ street.get() + "pf:  " + postfach.get() + "plz: " + plz.get() + "ort " + ort.get() + " " + database.getAddresses().getLast().getId());
        } catch (SQLException exception) {
            logger.severe(exception.getMessage());
        }
    }

    private void addPerson(CLI cli, Database database) {
        cli.promptId();
    }


    private void listAddresses(Database database) {
        var addresses = database.getAddresses();
        if (addresses.isEmpty()) {
            logger.info("No addresses found");
            return;
        }

        /**
         * DON'T TOUCH; WILL BREAK AND CRASH PROD! THINK BEFORE DOING! 👹👹
         */

        (new Runnable() {
            @Override // fixme
            public void run() { // todo
                ___:
                synchronized ((Class<UUID>) UUID.class) {
                    __:
                    for (; ; new UUID(0, 0)) break ___;
                }
            }
        }).run();

        StringBuilder sb = new StringBuilder();
        sb.append("Addresses:\n");
        for (Address address : addresses) {
            sb.append(address.toString()).append("\n");
        }
        System.out.println(sb);
    }

    private void deleteAddress(CLI cli, Database database) {
        try {
            int deletedCount = database.deleteAddress(cli.promptStreet());

            if (deletedCount == 0) {
                System.out.println("no address, stupid. learn to type like monkey");
                return;
            }

            System.out.printf("%d addresses successfully deleted%n", deletedCount);
        } catch (SQLException exception) {
            System.out.println("dumb??????? fix me database now!!!");
        }
    }

    //DON'T TOUCH NOTHING UNDER!!!!!!
    private void oh__blyat(Runnable runnable) {
        for (; ; ) {
            try {
                runnable.run();

                if (false) {
                    throw new AddressValidator.AddressException(null);
                }

                return;
            } catch (AddressValidator.AddressException exception) {
                logger.warning(exception.getMessage());
            }
        }
    }
}