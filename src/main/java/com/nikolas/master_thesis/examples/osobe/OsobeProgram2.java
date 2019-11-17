package com.nikolas.master_thesis.examples.osobe;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Dat je tok osoba u vidu metoda Osobe::osobeStream. Pomocu datog metoda,
 * lambda izraza i operacija nad tokovima podataka implementirati sledece metode
 * i pozvati ih iz glavnog programa:
 *
 * List<Osoba> opadajuceSortiraniPoDatumuRodjenja()
 * Set<Osoba> saVecimPrimanjimaOd(int)
 * double prosecnaPrimanjaUMestu(String)
 * Map<String, Double> prosecnaPrimanjaPoMestu()
 * Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad()
 * Map<Integer, List<Osoba>> razvrstaniPoBrojuDece()
 * Map<String, Map<Integer, List<Osoba>>> razvrstaniPoMestuIBrojuDece()
 * String gradSaNajviseDoseljenika()
 * String gradSaNajviseStarosedelaca()
 * String najbogatijiGrad()
 * String najpopularnijeMuskoIme()
 * String najpopularnijeZenskoIme()
 * 
 * U glavnom programu ispisati sledece podatke koriscenjem redukcija tokova
 * podataka (terminalna operacija .reduce()).
 *
 * Najbogatija osoba
 * -----------------
 * 01234567 Pera Peric
 *
 * Muska imena
 * -----------
 * Aca, Arsa, Bora, Vlada, ...
 *
 * Godina kada je rodjena najstarija osoba
 * ---------------------------------------
 * 1940
 *
 * U glavnom programu ispisati sledece podatke na zadati nacin. Racunanje podataka
 * realizovati pomocu operacije .collect(). Prvo podatke skupiti u mapu a potom
 * iz mape pustiti novi tok podataka i formatirati ispis pomocu metoda String::format.
 * Obratiti paznju na format ispisa, velika i mala slova i broj decimala. 
 *
 * Grad       | Broj ljudi | Prosecna primanja
 * -----------+------------+------------------
 * NOVI SAD   |        234 |          49867.56
 * BEOGRAD    |        322 |          50072.33
 * KRAGUJEVAC |        225 |          49215.45
 * ...
 *
 * Ime        | Br roditelja | Broj muske dece | Broj zenske dece
 * -----------+--------------+-----------------+-----------------
 * Pera       |          234 |             356 |              297
 * Mika       |          322 |             442 |              443
 * Jelena     |          225 |             295 |              312
 * ...
 *
 * Godine | Primanja
 * zivota | Najnize   | Najvise   | Ukupno     | Prosek    | Devijacija 
 * -------+-----------+-----------+------------+-----------+-----------
 * ...
 * 22     |  12600.00 | 102400.00 | 7652300.00 | 503476.12 |     132.66
 * 23     |  29600.00 |  99700.00 | 6843500.00 | 496456.26 |      98.32
 * 24     |  23400.00 | 123400.00 | 8134800.00 | 512388.43 |     253.01
 * ...  
 */
public class OsobeProgram2 {

	public static void main(String[] args) {

		System.out.println();
		System.out.println("Sortirani po datumu rodjenja:");
		opadajuceSortiraniPoDatumuRodjenja().stream()
				.map(o -> String.format("%s %s (%s)",
						o.getIme(),
						o.getPrezime(),
						o.getDatumRodjenja()))
				.forEach(System.out::println);
		
		System.out.println();
		System.out.println("Svi sa primanjima vecim od 100 000:");
		saVecimPrimanjimaOd(100_000).stream()
				.map(o -> String.format("%s %s (%d)",
						o.getIme(),
						o.getPrezime(),
						o.getPrimanja()))
				.forEach(System.out::println);
		
		System.out.println();
		System.out.println("Prosecna primanja za Novi Sad: " + prosecnaPrimanjaUMestu("Novi Sad"));

		System.out.println();
		System.out.println("Prosecna primanja:");
		prosecnaPrimanjaPoMestu().entrySet().stream()
				.map(e -> String.format("za %-15s %.2f",
						e.getKey(),
						e.getValue()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Najbogatiji za svaki grad:");
		saNajvecimPrimanjimaZaSvakiGrad().entrySet().stream()
				.map(e -> String.format("%-15s %s",
						e.getKey(),
						e.getValue()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Po broju dece:");
		razvrstaniPoBrojuDece().entrySet().stream()
				.map(e -> String.format("%d: %s",
						e.getKey(),
						e.getValue()))
				.forEach(System.out::println);
	
		System.out.println();
		System.out.println("Po mestu i broju dece:");
		razvrstaniPoMestuIBrojuDece().entrySet().stream()
				.peek(e -> System.out.println(e.getKey()))
				.flatMap(e -> e.getValue().entrySet().stream())
				.map(e -> String.format("%d: %s",
						e.getKey(),
						e.getValue()))
				.forEach(System.out::println);

		System.out.println();
		System.out.println("Grad sa najvise doseljenika: " + gradSaNajviseDoseljenika());

		System.out.println();
		System.out.println("Grad sa najvise starosedelaca: " + gradSaNajviseStarosedelaca());

		System.out.println();
		System.out.println("Najbogatiji grad: " + najbogatijiGrad());

		System.out.println();
		System.out.println("Najpopularnije musko ime: " + najpopularnijeMuskoIme());

		System.out.println();
		System.out.println("Najpopulatnije zensko ime: " + najpopularnijeZenskoIme());

		System.out.println();
		System.out.println("Najbogatija osoba");
		System.out.println("-----------------");
		Osoba najbogatija = Osobe.osobeStream(5000)
				.reduce(null, (o1, o2) -> {
					if (o1 == null) {
						return o2;
					}
					if (o1.getPrimanja() > o2.getPrimanja()) {
						return o1;
					}
					return o2;
				});
		System.out.println(najbogatija);

		System.out.println();
		System.out.println("Muska imena");
		System.out.println("-----------");
		String imena = Osobe.osobeStream(5000)
				.filter(o -> o.getPol() == Pol.MUSKI)
				.map(Osoba::getIme)
				.distinct()
				.sorted()
				.reduce("", (i1, i2) -> "".equals(i1) ? i2 : i1 + ", " + i2);
		System.out.println(imena);

		System.out.println();
		System.out.println("Godina kada je rodjena najstarija osoba");
		System.out.println("---------------------------------------");
		OptionalInt godina = Osobe.osobeStream(5000)
				.map(Osoba::getDatumRodjenja)
				.mapToInt(LocalDate::getYear)
				.reduce(Integer::min);
		System.out.println(godina.orElse(-1));

		Map<String, IntSummaryStatistics> podaci1 = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.summarizingInt(Osoba::getPrimanja)));

		System.out.println();
		System.out.println("Grad       | Broj ljudi | Prosecna primanja");
		System.out.println("-----------+------------+------------------");
		podaci1.entrySet().stream()
				.map(e -> String.format("%-10s | %10d | %17.2f",
						e.getKey().toUpperCase(),
						e.getValue().getCount(),
						e.getValue().getAverage()))
				.forEach(System.out::println);

		class Brojac {
			public int roditelji = 0;
			public int mDeca = 0;
			public int zDeca = 0;
		}

		Map<String, Brojac> podaci2 = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getIme, Collector.of(
						Brojac::new,
						(b, o) -> {
							b.roditelji++;
							b.mDeca += o.getBrDece(Pol.MUSKI);
							b.zDeca += o.getBrDece(Pol.ZENSKI);
						},
						(b1, b2) -> {
							b1.roditelji += b2.roditelji;
							b1.mDeca += b2.mDeca;
							b1.zDeca += b2.zDeca;
							return b1;
						})));

		System.out.println();
		System.out.println("Ime        | Br roditelja | Broj muske dece | Broj zenske dece");
		System.out.println("-----------+--------------+-----------------+-----------------");
		podaci2.entrySet().stream()
				.map(e -> String.format("%-10s | %12d | %15d | %16d",
						e.getKey(),
						e.getValue().roditelji,
						e.getValue().mDeca,
						e.getValue().zDeca))
				.forEach(System.out::println);

		class Statistike {

			public int br;
			public int suma;
			public int min;
			public int max;
			public double devijacija;

			public double prosek() {
				return (double) suma / br;
			}

			public void dodaj(int vrednost) { 
				br++;
				suma += vrednost;
				min = Integer.min(min, vrednost);
				max = Integer.max(max, vrednost);
			}
			
			public Statistike spoji(Statistike statistike) {
				br += statistike.br;
				suma += statistike.suma;
				min = Integer.min(min, statistike.min);
				max = Integer.max(max, statistike.max);
				return this;
			}
			
			public void devijacija(Stream<Integer> vrednosti) {
				double prosek = prosek();
				double v2n = vrednosti
						.mapToDouble(x -> x - prosek)
						.map(x -> x * x)
						.sum();
				devijacija = Math.sqrt(v2n / br);
			}
		}
		
		
		LocalDate sad = LocalDate.now();
		Map<Integer, Set<Integer>> razvrstano = Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						o -> o.getDatumRodjenja().until(sad).getYears(),
						Collectors.mapping(
								Osoba::getPrimanja,
								Collectors.toSet())));
		Map<Integer, Statistike> podaci3 = razvrstano.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						e -> e.getValue().stream()
								.collect(Collector.of(Statistike::new, Statistike::dodaj, Statistike::spoji))));
		razvrstano.entrySet().stream()
				.forEach(e -> {
					Statistike stat = podaci3.get(e.getKey());
					stat.devijacija(e.getValue().stream());
				});

		System.out.println();
		System.out.println("Godine | Primanja");
		System.out.println("zivota | Najnize   | Najvise   | Ukupno     | Prosek    | Devijacija");
		System.out.println("-------+-----------+-----------+------------+-----------+-----------");
		podaci3.entrySet().stream()
				.map(e -> String.format("%-6d | %6d.00 | %6d.00 | %7d.00 | %9.2f | %10.2f",
						e.getKey(),
						e.getValue().min,
						e.getValue().max,
						e.getValue().suma,
						e.getValue().prosek(),
						e.getValue().devijacija))
				.forEach(System.out::println);

	}

	private static List<Osoba> opadajuceSortiraniPoDatumuRodjenja() {
		return Osobe.osobeStream(5000)
				.sorted(Comparator.comparing(Osoba::getDatumRodjenja))
				.collect(Collectors.toList());
	}

	private static Set<Osoba> saVecimPrimanjimaOd(int granica) {
		return Osobe.osobeStream(5000)
				.filter(o -> o.getPrimanja() > granica)
				.collect(Collectors.toSet());
	}

	private static double prosecnaPrimanjaUMestu(String mesto) {
		return Osobe.osobeStream(5000)
				.filter(o -> mesto.equalsIgnoreCase(o.getMestoRodjenja()))
				.collect(Collectors.averagingInt(Osoba::getPrimanja));
	}

	private static Map<String, Double> prosecnaPrimanjaPoMestu() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.averagingInt(Osoba::getPrimanja)));
	}

	private static Map<String, Osoba> saNajvecimPrimanjimaZaSvakiGrad() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.collectingAndThen(
								Collectors.maxBy(Comparator.comparing(Osoba::getPrimanja)),
								Optional::get)));
	}

	private static Map<Integer, List<Osoba>> razvrstaniPoBrojuDece() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(Osoba::getBrDece));
	}

	private static Map<String, Map<Integer, List<Osoba>>> razvrstaniPoMestuIBrojuDece() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.groupingBy(Osoba::getBrDece)));
	}

	private static String gradSaNajviseDoseljenika() {
		return Osobe.osobeStream(5000)
				.filter(o -> !o.getMestoStanovanja().equals(o.getMestoRodjenja()))
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String gradSaNajviseStarosedelaca() {
		return Osobe.osobeStream(5000)
				.filter(o -> o.getMestoStanovanja().equals(o.getMestoRodjenja()))
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String najbogatijiGrad() {
		return Osobe.osobeStream(5000)
				.collect(Collectors.groupingBy(
						Osoba::getMestoStanovanja,
						Collectors.summingInt(Osoba::getPrimanja)))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String najpopularnijeMuskoIme() {
		return Osobe.osobeStream(5000)
				.filter(o -> o.getPol() == Pol.MUSKI)
				.map(Osoba::getIme)
				.collect(Collectors.groupingBy(
						Function.identity(),
						Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}

	private static String najpopularnijeZenskoIme() {
		return Osobe.osobeStream(5000)
				.filter(o -> o.getPol() == Pol.ZENSKI)
				.map(Osoba::getIme)
				.collect(Collectors.groupingBy(
						Function.identity(),
						Collectors.counting()))
				.entrySet().stream()
				.max(Map.Entry.comparingByValue())
				.map(Map.Entry::getKey)
				.orElse(null);
	}
}
