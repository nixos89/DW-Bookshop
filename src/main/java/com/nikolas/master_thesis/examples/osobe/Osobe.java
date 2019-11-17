package com.nikolas.master_thesis.examples.osobe;

import java.time.LocalDate;
import java.util.Random;
import java.util.stream.Stream;

public final class Osobe {

	private static final String[] IMENA = new String[] {
			"Per", "Mik", "Djok", "Raj", "Gaj", "Vlaj", "Zlaj", "Ars", "Bor",
			"Ac", "Jov", "Kost", "Nikol", "Vlad", "Vukot", "Život", "Siniš", "Puniš",
			"Nad", "Ned", "An", "Tanj", "Jelen", "Marij", "Tamar", "Nin", "Mil", "Mim", "Petr", "Ver",
			"Oliver", "Ivan", "Bojan", "Goran", "Zoran", "Dragan"};

	private static final String[] MESTA = new String[] {
			"Novi Sad", "Beograd", "Zrenjanin", "Sombor", "Subotica", "Kikinda", "Smederevo", "Kovin", "Kragujevac", "Ruma"
	};

	public static final Stream<Osoba> osobeStream(int n) {
		Random r = new Random(0);
		return Stream.generate(() -> osoba(r)).limit(n);
	}

	public static final Stream<String> stringStream(int n) {
		return osobeStream(n).map(o -> Osoba.toString(o));
	}

	private static final LocalDate START = LocalDate.of(1940, 1, 1);
	private static final Osoba osoba(Random r) {
		int index = r.nextInt(IMENA.length);
		String ime = IMENA[index] + "a";
		String prezime = IMENA[r.nextInt(IMENA.length)] + ((r.nextDouble() > 0.4) ? "ić" : r.nextDouble() > 0.5 ? "ović" : "ovski");
		Pol pol = index < IMENA.length / 2 ? Pol.MUSKI : Pol.ZENSKI;
		if (pol == Pol.ZENSKI && r.nextDouble() < 0.1) {
			prezime = prezime + "-" + IMENA[r.nextInt(IMENA.length)] + "ić";
		}
		String mesto1 = MESTA[r.nextInt(MESTA.length)];
		String mesto2 = MESTA[r.nextInt(MESTA.length)];
		LocalDate datum = START.plusDays(r.nextInt(20000));
		int primanja = 10 * (50_00 + (int)(50_00 * r.nextGaussian()));
		if (primanja < 5000) {
			primanja = 0;
		}
		Osoba[] deca = new Osoba[r.nextInt(6)];
		for (int i = 0; i < deca.length; i++) {
			int index2 = (index + i + 1) % IMENA.length;
			String ime2 = IMENA[index2] + "a";
			Pol pol2 = index2 < IMENA.length / 2 ? Pol.MUSKI : Pol.ZENSKI;
			LocalDate datum2 = datum.plusDays(7500 + r.nextInt(7500));
			if (datum2.getYear() > 2018) {
				datum2 = datum2.withYear(2018);
			}
			deca[i] = new Osoba(ime2, prezime, pol2, datum2, mesto2, mesto2, 0);
		}
		return new Osoba(ime, prezime, pol, datum, mesto1, mesto2, primanja, deca);
	}
}
