import java.util.*;
import java.io.*;

public class InventoryExtended extends Inventory{
	public void inquiryDataObat(){
		try{
			System.out.println("calling inquiryDataObat() from InventoryExtended class");
			Scanner dataObat = new Scanner(new File(getInventoryFile()));
			dataObat.nextLine();
			System.out.println("Berikut list data obat yang tersedia");

			while(dataObat.hasNext()){
				String baris = dataObat.nextLine();
				String[] data = baris.split(",");
					System.out.println(
						"Nama Barang: " + data[0]
						 + ", Qty: " + data[1]
						+ ", Harga: " + data[2]
					);
			}
			dataObat.close();
		} catch(Exception e){
			System.out.println("file data obat tidak ketemu..periksa kembali parameter yang dimasukkan");
		}
	}
}