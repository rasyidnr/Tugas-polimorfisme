import java.util.Scanner;

abstract class Member {
    protected int poin;
    protected double totalBelanja;

    public Member() {
        poin = 0;
        totalBelanja = 0;
    }

    public void kumpulkanPoin(double pembayaran) {
        int poinBaru = (int) (pembayaran / 10000);
        poin += poinBaru;
    }

    public double hitungDiskon() {
        if (totalBelanja >= 500000 && totalBelanja <= 1000000) {
            return 0.01; // Diskon 1%
        } else if (totalBelanja > 1000000 && totalBelanja <= 10000000) {
            return 0.03; // Diskon 3%
        } else if (totalBelanja > 10000000) {
            return 0.05; // Diskon 5%
        } else {
            return 0; // Tidak ada diskon
        }
    }

    protected double getTotalBelanja() {
        return totalBelanja;
    }

    protected void setTotalBelanja(double totalBelanja) {
        this.totalBelanja = totalBelanja;
    }

    public abstract void bayar(boolean menggunakanSaldo);
}

class MemberReguler extends Member {
    @Override
    public void bayar(boolean menggunakanSaldo) {
        double diskon = hitungDiskon();
        double totalPembayaran = totalBelanja - (totalBelanja * diskon);

        if (menggunakanSaldo) {
            if (totalPembayaran <= totalBelanja) {
                setTotalBelanja(getTotalBelanja() - totalPembayaran);
                System.out.println("Pembayaran berhasil menggunakan saldo.");
            } else {
                System.out.println("Saldo tidak mencukupi untuk pembayaran.");
            }
        } else {
            if (totalBelanja >= totalPembayaran) {
                setTotalBelanja(0);
                System.out.println("Pembayaran berhasil dengan uang tunai.");
            } else {
                System.out.println("Uang tidak mencukupi untuk pembayaran.");
            }
        }
    }
}

class MemberPlatinum extends Member {
    private double saldo;

    public MemberPlatinum(double saldoAwal) {
        saldo = saldoAwal;
    }

    @Override
    public void bayar(boolean menggunakanSaldo) {
        double diskon = hitungDiskon();
        double totalPembayaran = totalBelanja - (totalBelanja * diskon);

        if (menggunakanSaldo) {
            if (totalPembayaran <= saldo) {
                saldo -= totalPembayaran;
                System.out.println("Pembayaran berhasil menggunakan saldo.");
            } else {
                System.out.println("Saldo tidak mencukupi untuk pembayaran.");
            }
        } else {
            if (totalBelanja >= totalPembayaran) {
                saldo = 0;
                System.out.println("Pembayaran berhasil dengan uang tunai.");
            } else {
                System.out.println("Uang tidak mencukupi untuk pembayaran.");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        MemberReguler member1 = new MemberReguler();
        System.out.print("Masukkan total belanja untuk member 1: ");
        double totalBelanja1 = scanner.nextDouble();
        member1.setTotalBelanja(totalBelanja1);
        member1.kumpulkanPoin(totalBelanja1);
        double diskon1 = member1.hitungDiskon();
        member1.bayar(false); // false: membayar dengan uang tunai

        System.out.println("Member 1 mendapatkan diskon sebesar " + (diskon1 * 100) + "%.");
        System.out.println("Member 1 memiliki " + member1.poin + " poin.");

        MemberPlatinum member2 = new MemberPlatinum(500000);
        System.out.print("Masukkan total belanja untuk member 2: ");
        double totalBelanja2 = scanner.nextDouble();
        member2.setTotalBelanja(totalBelanja2);
        member2.kumpulkanPoin(totalBelanja2);
        double diskon2 = member2.hitungDiskon();
        member2.bayar(true); // true: membayar dengan menggunakan saldo

        System.out.println("Member 2 mendapatkan diskon sebesar " + (diskon2 * 100) + "%.");
        System.out.println("Member 2 memiliki " + member2.poin + " poin.");

        scanner.close();
    }
}
