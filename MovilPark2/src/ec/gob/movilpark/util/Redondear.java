package ec.gob.movilpark.util;

public class Redondear {
	
	public double Redondear(double numero)
	{
	      return Math.rint(numero*10000)/10000;
	}
	public double Redondear2(double numero)
	{
	      return Math.rint(numero*100)/100;
	}
	
	public static void main(String[] args) {
		Redondear red = new Redondear();
		System.out.println(red.Redondear2(12.3999999999));
				
	}

}
