// Definición de excepción personalizada para saldo insuficiente
class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}

// Definición de excepción personalizada para monto negativo
class MontoInvalidoException extends Exception {
    public MontoInvalidoException(String mensaje) {
        super(mensaje);
    }
}

// Clase que representa una cuenta bancaria
class Cuenta {
    private String titular;
    private double saldo;

    public Cuenta(String titular, double saldoInicial) {
        this.titular = titular;
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }

    // Método para depositar dinero
    public void depositar(double monto) throws MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto a depositar debe ser positivo.");
        }
        saldo += monto;
    }

    // Método para retirar dinero
    public void retirar(double monto) throws SaldoInsuficienteException, MontoInvalidoException {
        if (monto <= 0) {
            throw new MontoInvalidoException("El monto a retirar debe ser positivo.");
        }
        if (monto > saldo) {
            throw new SaldoInsuficienteException("Saldo insuficiente para realizar el retiro.");
        }
        saldo -= monto;
    }

    // Método para transferir dinero a otra cuenta
    public void transferir(Cuenta destino, double monto) throws SaldoInsuficienteException, MontoInvalidoException {
        // Primero retiramos de la cuenta actual
        this.retirar(monto);
        // Luego depositamos en la cuenta de destino
        destino.depositar(monto);
    }
}

// Clase principal BancoApp para ejecutar las operaciones
public class Main {
    public static void main(String[] args) {
        try {
            // Crear cuentas con saldos iniciales
            Cuenta cuenta1 = new Cuenta("Alice", 1000);
            Cuenta cuenta2 = new Cuenta("Bob", 500);

            // Demostrar operaciones bancarias
            System.out.println("Saldo inicial de " + cuenta1.getTitular() + ": " + cuenta1.getSaldo());
            System.out.println("Saldo inicial de " + cuenta2.getTitular() + ": " + cuenta2.getSaldo());

            // Depositar dinero en la cuenta 1
            cuenta1.depositar(300);
            System.out.println("Después de depositar 300, saldo de " + cuenta1.getTitular() + ": " + cuenta1.getSaldo());

            // Retirar dinero de la cuenta 2
            cuenta2.retirar(100);
            System.out.println("Después de retirar 100, saldo de " + cuenta2.getTitular() + ": " + cuenta2.getSaldo());

            // Transferir dinero de cuenta1 a cuenta2
            cuenta1.transferir(cuenta2, 200);
            System.out.println("Después de transferir 200 de " + cuenta1.getTitular() + " a " + cuenta2.getTitular() + ":");
            System.out.println(cuenta1.getTitular() + " saldo: " + cuenta1.getSaldo());
            System.out.println(cuenta2.getTitular() + " saldo: " + cuenta2.getSaldo());

            // Intentar retirar más dinero del que se tiene
            cuenta2.retirar(1000);  // Esto lanzará una excepción

        } catch (SaldoInsuficienteException | MontoInvalidoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
