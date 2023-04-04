import java.awt.desktop.SystemSleepEvent;
import java.awt.image.AreaAveragingScaleFilter;
import java.io.IOException;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class WebParser {
    private final String baseURL;
    private Document currentDoc;

    public WebParser(String url) {
        this.baseURL = url;
        try {
            this.currentDoc = Jsoup.connect(this.baseURL).get();
        } catch (IOException e) {
        }
    }

    public void getDiscontinuedAwards(){
        ArrayList<String> discontinuedAwards = new ArrayList<>();
        Elements listOfAwards = this.currentDoc.select("div.div-col").select("li");

        for (Element item : listOfAwards) {
            Element a = item.select("a").first();
            if (a != null) {
                String award = a.text();
                discontinuedAwards.add(award);
            }
            else{
                String award = item.text();
                int value = award.indexOf(":");
                discontinuedAwards.add(award.substring(0, value));
            }
        }

        System.out.println("The following are awards that have been discontinued:");
        for(String item: discontinuedAwards){
            System.out.println(item);
        }


    }

    public void awardCategoriesAdded(int year){
        int value = Integer.parseInt(Integer.toString(year).substring(0,3));

        ArrayList<String> awardCategories = new ArrayList<>();

        Elements awardTable = this.currentDoc.select("table.wikitable.sortable").first()
                .select("tbody")
                .select("tr");

        for(Element item: awardTable) {
            Elements info = item.select("td");
            if (info.size() > 0){
                String yearInfo = info.text();
                if(Integer.parseInt(yearInfo.substring(0,3)) == value){
                    int placing = yearInfo.indexOf(" ");
                    String information = yearInfo.substring(placing + 1);
                    awardCategories.add(information);
                }
            }
        }

        System.out.println("The award categories that were added in the " + year +
                "s are as follows: ");
        for(String item: awardCategories){
            System.out.println(item);
        }


    }

    public void nominatedMinimumNumberOfAwards(int numberOfNominations, int year) throws IOException {
        Element a = this.currentDoc.select("a:contains(List of Academy Awards ceremonies)").first();
        String link = a.absUrl("href");
        Document d = Jsoup.connect(link).get();

        Element table = d.select("table.sortable.wikitable").first();
        Elements rows = table.select("tr");

        int value = year - 1929 + 1;

        HashMap<String, Integer> movieMap = new HashMap<>();

        int i = 0;
        for(Element row: rows){
            Element a2 = row.select("a").first();
            if(i == value){
                String link2 = a2.absUrl("href");
                Document d2 = Jsoup.connect(link2).get();
                Element tableOfMovies = d2.select("table.wikitable").first();
                Elements Movies = tableOfMovies.select("i");
                for(Element movie: Movies){
                    String movieName = movie.select("a").text();
                    if(movieMap.containsKey(movieName)){
                        int numberOfAwards = movieMap.get(movieName) + 1;
                        movieMap.put(movieName, numberOfAwards);
                    }else{
                        movieMap.put(movieName, 1);
                    }
                }
            }
            i++;
        }

        System.out.println("Here are the movies with at least " + numberOfNominations +
                " nominations in " + year + ":");
        for (Map.Entry<String, Integer> entry : movieMap.entrySet()) {
            String movie = entry.getKey();
            int numberOfNom = entry.getValue();
            if(numberOfNom >= numberOfNominations){
                System.out.println(movie);
            }
        }

    }

    public void filmWonSpecificAwardInYear(String awardName, int year) throws IOException {
        String filmName = "Empty Movie";
        Element a = this.currentDoc.select("a:contains(List of Academy Awards ceremonies)").first();
        String link = a.absUrl("href");
        Document d = Jsoup.connect(link).get();

        Element table = d.select("table.sortable.wikitable").first();
        Elements rows = table.select("tr");

        boolean nextOne = false;

        int value = year - 1929 + 1;
        int i = 0;
        for(Element row: rows) {
            Element a2 = row.select("a").first();
            if (i == value) {
                String link2 = a2.absUrl("href");
                Document d2 = Jsoup.connect(link2).get();
                Element tableOfMovies = d2.select("table.wikitable").first();
                Elements moviesAndAwards = tableOfMovies.select("tr").select("b");
                for(Element movieOrAward: moviesAndAwards){
                    if(nextOne){
                        filmName = movieOrAward.select("a").first().text();
                        break;
                    }else{
                        if(movieOrAward.text().equals(awardName)){
                            nextOne = true;
                        }
                    }
                }



            }
            i++;


        }

        System.out.println("The film that won the " + awardName + " in " + year + " was " +
                filmName + ".");

    }

    public void budgetAndBoxOfficeForAwardWinnerInSpecificYear(String awardName, int year) throws IOException {
        String filmName = "Empty Movie";
        String budget = "$0";
        String boxOffice = "$0";
        Element a = this.currentDoc.select("a:contains(List of Academy Awards ceremonies)").first();
        String link = a.absUrl("href");
        Document d = Jsoup.connect(link).get();

        Element table = d.select("table.sortable.wikitable").first();
        Elements rows = table.select("tr");

        boolean nextOne = false;

        int value = year - 1929 + 1;
        int i = 0;
        for(Element row: rows) {
            Element a2 = row.select("a").first();
            if (i == value) {
                String link2 = a2.absUrl("href");
                Document d2 = Jsoup.connect(link2).get();
                Element tableOfMovies = d2.select("table.wikitable").first();
                Elements moviesAndAwards = tableOfMovies.select("tr").select("b");
                for(Element movieOrAward: moviesAndAwards){
                    if(nextOne){
                        filmName = movieOrAward.select("a").first().text();
                        break;
                    }else{
                        if(movieOrAward.text().equals(awardName)){
                            nextOne = true;
                        }
                    }
                }

                String link3 = moviesAndAwards.select("a:contains(" + filmName + ")")
                        .first().absUrl("href");

                Document d3 = Jsoup.connect(link3).get();

                budget = d3.select("tr:contains(Budget)")
                        .select("td.infobox-data").first().text();
                boxOffice = d3.select("tr:contains(Box Office)")
                        .select("td.infobox-data").first().text();

                if(budget.contains("[")){
                    int val = budget.indexOf("[");
                    budget = budget.substring(0,val);
                }

                if(boxOffice.contains("[")){
                    int val = boxOffice.indexOf("[");
                    boxOffice = boxOffice.substring(0,val);
                }


                break;
            }
            i++;
        }

        System.out.println("The film that won the " + awardName + " in " + year + " was " +
                filmName + ". This movie had a budget of " + budget + ". The total amount it made in the box office " +
                "was " + boxOffice + ".");

    }


    public void academicInstitutionWithMostNominationsForSpecificAward(String awardName) throws IOException {
        HashMap<String, Integer> universityCount = new HashMap<>();
        ArrayList<String> foundActors = new ArrayList<>();
        Element a = this.currentDoc.select("a:contains(List of Academy Awards ceremonies)").first();
        String link = a.absUrl("href");
        Document d = Jsoup.connect(link).get();
        Element table = d.select("table.sortable.wikitable").first();
        Elements rows = table.select("tr");
        for(Element row: rows) {
            Element a2 = row.select("a").first();
            if(a2 != null){
                String link2 = a2.absUrl("href");
                Document d2 = Jsoup.connect(link2).get();
                Element tableOfAwards = d2.select("table.wikitable").first();
                Element section = tableOfAwards.select("td:contains(" + awardName +")").first();
                if (section != null){
                    Elements actorsList = section.select("li");
                    for(Element actor: actorsList){
                        Element a3 = actor.select("li").select("a").first();
                        String actorName = a3.text();
                        if(!foundActors.contains(actorName)){
                            foundActors.add(actorName);
                            String url2 = a3.absUrl("href");
                            Document d3 = Jsoup.connect(url2).get();
                            Element almaMaterInfo = d3.select("tr:contains(Alma mater)")
                                    .select("td.infobox-data").first();

                            if (almaMaterInfo != null){
                                String educationalInstitution = almaMaterInfo.text();
                                if (educationalInstitution.contains(" (")){
                                    educationalInstitution = educationalInstitution.substring(0,
                                            educationalInstitution.indexOf(" ("));
                                }
                                if(universityCount.containsKey(educationalInstitution)){
                                    int value = universityCount.get(educationalInstitution);
                                    universityCount.put(educationalInstitution, value + 1);
                                }
                                else{
                                    universityCount.put(educationalInstitution, 1);
                                }
                            }

                            Element educationInfo = d3.select("tr:contains(Education)")
                                    .select("td.infobox-data").first();

                            if (educationInfo != null){
                                String educationalInstitution = educationInfo.text();
                                if (educationalInstitution.contains(" (")){
                                    educationalInstitution = educationalInstitution.substring(0,
                                            educationalInstitution.indexOf(" ("));
                                }
                                if(universityCount.containsKey(educationalInstitution)){
                                    int value = universityCount.get(educationalInstitution);
                                    universityCount.put(educationalInstitution, value + 1);
                                }
                                else{
                                    universityCount.put(educationalInstitution, 1);
                                }
                            }
                        }
                    }
                }
                else{
                    ArrayList<String> awardPlacing = new ArrayList<>();
                    ArrayList<Element> awardSection = new ArrayList<>();
                    Elements allRows1 = tableOfAwards.select("tr:contains(" + awardName + ")")
                            .select("th");
                    for(Element awardTitle: allRows1){
                        awardPlacing.add(awardTitle.text());
                    }
                        Elements specificRow = tableOfAwards.select("tr:contains(" + awardName + ") + tr")
                                .select("td");
                        for(Element row2: specificRow){
                            awardSection.add(row2);
                        }

                        for(int k = 0; k < awardPlacing.size(); k++){
                            if(awardPlacing.get(k).equals(awardName)){
                                Elements correctInfo = awardSection.get(k).select("li");
                                for(Element info: correctInfo){
                                    Element a4 = info.select("li").select("a").first();
                                    String actorName = a4.text();
                                    if(!foundActors.contains(actorName)){
                                        foundActors.add(actorName);
                                        String url2 = a4.absUrl("href");
                                        Document d4 = Jsoup.connect(url2).get();
                                        Element almaMaterInfo = d4.select("tr:contains(Alma mater)")
                                                .select("td.infobox-data").first();

                                        if (almaMaterInfo != null){
                                            String educationalInstitution = almaMaterInfo.text();
                                            if (educationalInstitution.contains(" (")){
                                                educationalInstitution = educationalInstitution.substring(0,
                                                        educationalInstitution.indexOf(" ("));
                                            }
                                            if(universityCount.containsKey(educationalInstitution)){
                                                int value = universityCount.get(educationalInstitution);
                                                universityCount.put(educationalInstitution, value + 1);
                                            }
                                            else{
                                                universityCount.put(educationalInstitution, 1);
                                            }
                                        }

                                        Element educationInfo = d4.select("tr:contains(Education)")
                                                .select("td.infobox-data").first();

                                        if (educationInfo != null){
                                            String educationalInstitution = educationInfo.text();
                                            if (educationalInstitution.contains(" (")){
                                                educationalInstitution = educationalInstitution.substring(0,
                                                        educationalInstitution.indexOf(" ("));
                                            }
                                            if(universityCount.containsKey(educationalInstitution)){
                                                int value = universityCount.get(educationalInstitution);
                                                universityCount.put(educationalInstitution, value + 1);
                                            }
                                            else{
                                               universityCount.put(educationalInstitution, 1);
                                            }
                                        }



                                    }
                                }
                            }
                        }
                    }

            }





            }

        String institution = "";
        int highestCount = 0;

        for (Map.Entry<String, Integer> entry : universityCount.entrySet()) {
            String educationalInstitution = entry.getKey();
            int count = entry.getValue();
            if(count > highestCount){
                institution = educationalInstitution;
                highestCount = count;
            }
            else if(count == highestCount){
                institution = institution + "," + educationalInstitution;
            }
        }

        ArrayList<String> finalAnswer = new ArrayList<>();

        if (institution.contains(",")){
            String[] tieValues = institution.split(",");
            finalAnswer.addAll(Arrays.asList(tieValues));
        }
        else{
            finalAnswer.add(institution);
        }

        System.out.println("The Academic Insitution(s) that had the most nominees to the " + awardName +
                " award with " + highestCount + " nominees is the following: ");
        for(int k = 0; k < finalAnswer.size(); k++){
            System.out.println(finalAnswer.get(k));
        }

        }


        public void numberOfTimesCountryNominatedOrWonAward(String awardName) throws IOException {
            Elements awardTable = this.currentDoc.select("table.wikitable.sortable").first()
                    .select("tbody")
                    .select("tr");

            Element a = awardTable.select("a:contains(" + awardName +")").first();

            HashMap<String, Integer> countryAndCount = new HashMap<>();

            String link = a.absUrl("href");
            Document d = Jsoup.connect(link).get();
            if(d.select("h2:contains(Winners and nominees)").first() != null){
                String link2 = d.select("div:contains(" + awardName +")").first()
                        .select("a:contains(" + awardName +")").first().absUrl("href");
                Document d2 = Jsoup.connect(link2).get();
                Elements allTables = d2.select("table.wikitable:contains(Film)");
                for(Element table: allTables){
                    Elements rows = table.select("tr");
                    Element rowWithTitles = rows.select("tr:contains(Film)").first();
                    Elements titleColumns = rowWithTitles.select("th");
                    ArrayList<String> columnNames = new ArrayList<>();
                    for(Element titleColumn: titleColumns){
                        columnNames.add(titleColumn.text());
                    }
                    int j = 0;
                    for(int i = 0; i < columnNames.size(); i++){
                        if(columnNames.get(i).contains("Film")){
                            j = i;
                            break;
                        }
                    }
                    for(Element row: rows){
                        ArrayList<Element> allRowElements = new ArrayList<>();
                        Elements columnsInRow = row.select("td");
                        for(Element columnInRow: columnsInRow){
                            allRowElements.add(columnInRow);
                        }
                        try{
                            if(allRowElements.size() > 0){
                                Element movieCol = allRowElements.get(j - 1);
                                Element a3 = movieCol.select("a").first();
                                if(a3 != null){
                                    String link3 = a3.absUrl("href");
                                    Document d3 = Jsoup.connect(link3).get();
                                    String CountryInfo = "";
                                    try{
                                        CountryInfo = d3.select("tr:contains(Country)")
                                                .select("td.infobox-data").first().text();
                                    }
                                    catch(Exception e){
                                        CountryInfo = d3.select("tr:contains(Countries)")
                                                .select("td.infobox-data").select("#text").first().text();
                                    }
                                    if(CountryInfo.contains("[")){
                                        CountryInfo = CountryInfo.substring(0, CountryInfo.indexOf("["));
                                    }

                                    if(countryAndCount.containsKey(CountryInfo)){
                                        int value = countryAndCount.get(CountryInfo) + 1;
                                        countryAndCount.put(CountryInfo, value);
                                    }else{
                                        countryAndCount.put(CountryInfo, 1);
                                    }
                                }

                            }
                        }
                        catch (Exception e){
                            continue;
                        }

                    }
                }
            }
            else{
                Elements allTables = d.select("table.wikitable:contains(Film)");
                for(Element table: allTables){
                    Elements rows = table.select("tr");
                    Element rowWithTitles = rows.select("tr:contains(Film)").first();
                    Elements titleColumns = rowWithTitles.select("th");
                    ArrayList<String> columnNames = new ArrayList<>();
                    for(Element titleColumn: titleColumns){
                        columnNames.add(titleColumn.text());
                    }
                    int j = 0;
                    for(int i = 0; i < columnNames.size(); i++){
                        if(columnNames.get(i).contains("Film")){
                            j = i;
                            break;
                        }
                    }
                    for(Element row: rows){
                        ArrayList<Element> allRowElements = new ArrayList<>();
                        Elements columnsInRow = row.select("td");
                        for(Element columnInRow: columnsInRow){
                            allRowElements.add(columnInRow);
                        }
                        try{
                            if(allRowElements.size() > 0){
                                Element movieCol = allRowElements.get(j - 1);
                                Element a3 = movieCol.select("a").first();
                                if(a3 != null){
                                    String link3 = a3.absUrl("href");
                                    Document d3 = Jsoup.connect(link3).get();
                                    String CountryInfo = "";
                                    try{
                                        CountryInfo = d3.select("tr:contains(Country)")
                                                .select("td.infobox-data").first().text();
                                    }
                                    catch(Exception e){
                                        CountryInfo = d3.select("tr:contains(Countries)")
                                                .select("td.infobox-data").select("#text").first().text();
                                    }
                                    if(CountryInfo.contains("[")){
                                        CountryInfo = CountryInfo.substring(0, CountryInfo.indexOf("["));
                                    }

                                    if(countryAndCount.containsKey(CountryInfo)){
                                        int value = countryAndCount.get(CountryInfo) + 1;
                                        countryAndCount.put(CountryInfo, value);
                                    }else{
                                        countryAndCount.put(CountryInfo, 1);
                                    }
                                }

                            }
                        }
                        catch (Exception e){
                            continue;
                        }

                    }
                }
            }

           System.out.println("The following are the number of times a country's film has been nominated for or won" +
                   " the " + awardName + " award:");
            for (Map.Entry<String, Integer> entry : countryAndCount.entrySet()) {
                String country = entry.getKey();
                int count = entry.getValue();
                System.out.println("- " + country + ": " + count);
            }

        }

        public void getFilmsThatWerePartOfAllFourAwards(String awardName1, String awardName2, String awardName3,
                                                         String awardName4, int year) throws IOException {
            Element a = this.currentDoc.select("a:contains(List of Academy Awards ceremonies)").first();
            String link = a.absUrl("href");
            Document d = Jsoup.connect(link).get();

            Element table = d.select("table.sortable.wikitable").first();
            Elements rows = table.select("tr");

            int value = year - 1929 + 1;

            HashMap<Integer, HashMap<String, Integer>> yearWithMovies = new HashMap<>();

            ArrayList<String> awardNames = new ArrayList<>();

            awardNames.add(awardName1);
            awardNames.add(awardName2);
            awardNames.add(awardName3);
            awardNames.add(awardName4);

            int i = 1;
            for(Element row: rows){
                HashMap<String, Integer> movieMap = new HashMap<>();
                Element a2 = row.select("a").first();
                if(a2 != null){
                    String link2 = a2.absUrl("href");
                    Document d2 = Jsoup.connect(link2).get();
                    Element tableOfAwards = d2.select("table.wikitable").first();
                    for(String awardName: awardNames){
                        Element section = tableOfAwards.select("td:contains(" + awardName +")").first();
                        if (section != null){
                            Elements moviesList = section.select("li");
                            for(Element movie: moviesList){
                                Element a3 = movie.select("li").select("i").first();
                                String movieName = a3.text();
                                if(movieMap.containsKey(movieName)){
                                    value = movieMap.get(movieName);
                                    movieMap.put(movieName, value + 1);
                                }else{
                                    movieMap.put(movieName, 1);
                                }
                            }
                        }
                        else{
                            ArrayList<String> awardPlacing = new ArrayList<>();
                            ArrayList<Element> awardSection = new ArrayList<>();
                            Elements allRows1 = tableOfAwards.select("tr:contains(" + awardName + ")")
                                    .select("th");
                            for(Element awardTitle: allRows1){
                                awardPlacing.add(awardTitle.text());
                            }
                            Elements specificRow = tableOfAwards.select("tr:contains(" + awardName + ") + tr")
                                    .select("td");
                            for(Element row2: specificRow){
                                awardSection.add(row2);
                            }

                            for(int k = 0; k < awardPlacing.size(); k++){
                                if(awardPlacing.get(k).equals(awardName)){
                                    Elements correctInfo = awardSection.get(k).select("li");
                                    for(Element info: correctInfo){
                                        Element a4 = info.select("li").select("i").first();
                                        String movieName = a4.text();
                                        if(movieMap.containsKey(movieName)){
                                            value = movieMap.get(movieName);
                                            movieMap.put(movieName, value + 1);
                                        }else{
                                            movieMap.put(movieName, 1);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                int thisYear = 1929 + i - 2;
                yearWithMovies.put(thisYear, movieMap);
                i++;
            }
            System.out.println("The following are the films that won the " + awardName1 + ", " + awardName2 + ", "
                    + awardName3 + ", and " + awardName4 + " awards in each year:");
            for (Map.Entry<Integer, HashMap<String, Integer>> entry : yearWithMovies.entrySet()) {
                int year1 = entry.getKey();
               HashMap<String, Integer> movieMap = entry.getValue();
               ArrayList<String> moviesInAllFour = new ArrayList<>();
               for(Map.Entry<String, Integer> entry2 : movieMap.entrySet()){
                   String movie = entry2.getKey();
                   int count = entry2.getValue();
                   if(count == 4){
                       moviesInAllFour.add(movie);
                   }
               }
               if(moviesInAllFour.size() > 0){
                   System.out.println("In " + year1 + ", the films were ");
                   for(String movie2: moviesInAllFour){
                       System.out.println("- " + movie2);
                   }
               }

            }
        }

        public void getNumberOfTimesActorWonOscar(String actorName) throws IOException {

            ArrayList<String> discontinuedAwards = new ArrayList<>();
            Elements listOfAwards = this.currentDoc.select("div.div-col").select("li");

            for (Element item : listOfAwards) {
                Element a = item.select("a").first();
                if (a != null) {
                    String award = a.text();
                    discontinuedAwards.add(award);
                }
                else{
                    String award = item.text();
                    int value = award.indexOf(":");
                    discontinuedAwards.add(award.substring(0, value));
                }
            }

            ArrayList<String> awardNames = new ArrayList<>();
            int countForActor = 0;
            Elements awardTable = this.currentDoc.select("table.wikitable.sortable").first()
                    .select("tbody")
                    .select("tr");

            for(Element item: awardTable) {
                Elements info = item.select("td");
                if (info.size() > 0){
                    String yearInfo = info.text();
                    int placing = yearInfo.indexOf(" ");
                    String information = yearInfo.substring(placing + 1);
                    awardNames.add(information);
                }
            }

            for(String award: discontinuedAwards){
                awardNames.add(award);
            }

            Element a = this.currentDoc.select("a:contains(List of Academy Awards ceremonies)").first();
            String link = a.absUrl("href");
            Document d = Jsoup.connect(link).get();

            Element table = d.select("table.sortable.wikitable").first();
            Elements rows = table.select("tr");

            for(Element row: rows){
                Element a2 = row.select("a").first();
                if(a2 != null) {
                    String link2 = a2.absUrl("href");
                    Document d2 = Jsoup.connect(link2).get();
                    Element tableOfAwards = d2.select("table.wikitable").first();
                    for (String awardName : awardNames) {
                        Element section = tableOfAwards.select("td:contains(" + awardName + ")").first();
                        if (section != null) {
                            Elements actorsList = section.select("li");
                            for (Element actor : actorsList) {
                                Element a3 = actor.select("li").select("a").first();
                                if(a3 != null){
                                    String actorName2 = a3.text();
                                    if(actorName2.equals(actorName)){
                                        countForActor++;
                                    }
                                }
                            }
                        } else {
                            ArrayList<String> awardPlacing = new ArrayList<>();
                            ArrayList<Element> awardSection = new ArrayList<>();
                            Elements allRows1 = tableOfAwards.select("tr:contains(" + awardName + ")")
                                    .select("th");
                            for (Element awardTitle : allRows1) {
                                awardPlacing.add(awardTitle.text());
                            }
                            Elements specificRow = tableOfAwards.select("tr:contains(" + awardName + ") + tr")
                                    .select("td");
                            for (Element row2 : specificRow) {
                                awardSection.add(row2);
                            }

                            for (int k = 0; k < awardPlacing.size(); k++) {
                                if (awardPlacing.get(k).equals(awardName)) {
                                    Elements correctInfo = awardSection.get(k).select("li");
                                    for (Element info : correctInfo) {
                                        Element a4 = info.select("li").select("a").first();
                                        if(a4 != null){
                                            String actorName2 = a4.text();
                                            if(actorName2.equals(actorName)){
                                                countForActor++;
                                            }
                                        }

                                    }
                                }
                            }
                        }

                    }
                }
            }
            if(countForActor == 0){
                System.out.println("It looks like " + actorName + " has never been nominated for or won an Oscar" +
                        "before.");
            }
            else{
                System.out.println("The number of times that the actor or actress " + actorName + " has been nominated"
                        + " for or won an oscar award is " + countForActor + " times!");
            }


        }




    }










