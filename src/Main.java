import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        WebParser parser = new WebParser("https://en.wikipedia.org/wiki/Academy_Awards");

        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the number of the question that you would like answered: \n" +
                "1. List all Discontinued Categories for Awards. \n" +
                "2. How many award categories were added in the [INSERT YEAR]s? \n" +
                "3. List all the films that were nominated for at least [INSERT NUMBER OF AWARDS] awards in " +
                "[INSERT YEAR]. \n" +
                "4. Which film won [INSERT AWARD NAME] in [INSERT YEAR]? \n" +
                "5. What was the budget for the [INSERT AWARD NAME] winner in 2022? " +
                "How much did this movie make in the box office? \n" +
                "6. Which academic institution (university, college, etc.) has the highest number of alumni " +
                "nominated for the [INSERT AWARD NAME] award? \n" +
                "7. For [INSERT AWARD NAME], for the countries that were nominated/won, how many times " +
                "have they been nominated in the past (including this year)? \n" +
                "8. How many times has [INSERT ACTOR NAME HERE] been nominated for or won an Oscar award? \n" +
                "EC1. The most prestigious awards at the Oscars are the Best Picture, Best Director, Best Actor," +
                " and Best Actress. Were any movies nominated for all 4 of these awards in 2022? What about" +
                " in all the years that the Oscars ceremonies were held so far?\n");

        String questionNumber = sc.nextLine();

        if(questionNumber.equals("1")){
            parser.getDiscontinuedAwards();
        }

        if(questionNumber.equals("2")){
            System.out.println("What is the year?");
            int year = Integer.parseInt(sc.nextLine());
            parser.awardCategoriesAdded(year);
        }

        if(questionNumber.equals("3")){
            System.out.println("What is the number of awards?");
            int nominations = Integer.parseInt(sc.nextLine());
            System.out.println("What is the year?");
            int year = Integer.parseInt(sc.nextLine());
            parser.nominatedMinimumNumberOfAwards(nominations, year);
        }

        if(questionNumber.equals("4")){
            System.out.println("What is the name of the award?");
            String awardName = sc.nextLine();
            System.out.println("What is the year?");
            int year = Integer.parseInt(sc.nextLine());
            parser.filmWonSpecificAwardInYear(awardName, year);
        }

        if(questionNumber.equals("5")){
            System.out.println("What is the name of the award?");
            String awardName = sc.nextLine();
            parser.budgetAndBoxOfficeForAwardWinnerInSpecificYear(awardName, 2022);
        }

        if(questionNumber.equals("6")){
            System.out.println("What is the name of the award?");
            String awardName = sc.nextLine();
            parser.academicInstitutionWithMostNominationsForSpecificAward(awardName);
        }

        if(questionNumber.equals("7")){
            System.out.println("What is the name of the award?");
            String awardName = sc.nextLine();
            parser.numberOfTimesCountryNominatedOrWonAward(awardName);
        }

        if(questionNumber.equals("8")){
            System.out.println("What is the name of the actor?");
            String actorName = sc.nextLine();
            parser.getNumberOfTimesActorWonOscar(actorName);
        }

        if(questionNumber.equals("EC1")){
            parser.getFilmsThatWerePartOfAllFourAwards("Best Picture", "Best Director",
                    "Best Actor", "Best Actress", 2022);
        }

    }
}