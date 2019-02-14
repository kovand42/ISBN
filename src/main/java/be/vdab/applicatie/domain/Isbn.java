package be.vdab.applicatie.domain;
import java.util.Set;
import java.util.HashSet;
public class Isbn {
	private static final long KLEINSTE_GETAL_MET_13_CIJFERS = 1000000000000L;
	private static final long GROOTSTE_GETAL_MET_13_CIJFERS = 9999999999999L;
	private static final Set<Short> MOGELIJKE_EERSTE_3_CIJFERS = new HashSet<>();
	private final long nummer;
	static {
		// deze static initializer wordt één keer uitgevoerd in het programma,
		// als je de eerste keer de class Isbnr aanspreekt.
		// je kan in deze static initializer enkel static variabelen manipuleren
		MOGELIJKE_EERSTE_3_CIJFERS.add((short) 978);
		MOGELIJKE_EERSTE_3_CIJFERS.add((short) 979);
	}
	public Isbn(long nummer) {
		if(nummer < KLEINSTE_GETAL_MET_13_CIJFERS
				|| nummer > GROOTSTE_GETAL_MET_13_CIJFERS) {
			throw new IllegalArgumentException("Bevat geen 13 cijfers");
		}
		short eerste3Cijfers = (short) (nummer / 10000000000L);
		if(!MOGELIJKE_EERSTE_3_CIJFERS.contains(eerste3Cijfers)) {
			throw new IllegalArgumentException("Begint niet met " + MOGELIJKE_EERSTE_3_CIJFERS);
		}
		long somEvenCijfers = 0;
		long somOnevenCijfers = 0;
		long teVerwerkenCijfers = nummer / 10;
		for(int teller = 0; teller != 6; teller++) {
			somEvenCijfers += teVerwerkenCijfers % 10;
			teVerwerkenCijfers /= 10;
			somOnevenCijfers += teVerwerkenCijfers % 10;
			teVerwerkenCijfers /= 10;
		}
		long controleGetal = somEvenCijfers * 3 + somOnevenCijfers;
		long naastGelegenHoger10Tal = controleGetal - controleGetal % 10 + 10;
		long verschil = naastGelegenHoger10Tal - controleGetal;
		long laatsteCijfer = nummer % 10;
		if (verschil == 10) {
			if (laatsteCijfer != 0) {
		        throw new IllegalArgumentException("Verkeerd controlegetal");
			}
		}else {
		    if (laatsteCijfer != verschil) {
			    throw new IllegalArgumentException("Verkeerd controlegetal");
			}
		}
		this.nummer = nummer;
	}
	@Override
	public String toString() {
		return String.valueOf(nummer);
	}
}
