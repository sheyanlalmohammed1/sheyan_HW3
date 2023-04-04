Sheyan Lalmohammed
04/03/2023
Homework 3


The following is my assumptions and information on the functions for each question in the Web Parser Class:

Question 1: Question 1 is about all the discontinued categories for awards. The assumption I made for this question
was that all the information about the discontinued categories could be found in a section on the original Wikipedia
page for the academy awards which was titled "Discontinued Categories" and that each of the awards that was
discontinued was in an <a> tag within this section. The function awardCategoriesAdded() is responsible for dealing
with the output of this question.

Question 2: Question 2 is about how many award categories were added in a specific decade. The assumptions that I made
for this question was that all the information about award categories that were added to the Academy Awards could be
found in a table on the wikipedia page for the Academy Awards which is titled "List of current Awards of Merit
categories by year introduced, sortable by category". I assumed that in each row of this table, the year was in the
left-most column and that the category added was in an <a> tag in the column to the right. The function
awardCategoriesAdded() is responsible for dealing with the output for this question and takes the year as input.

Question 3: Question 3 is about finding all the films that were nominated for at least a specific number of awards
in a specific year. The assumptions that I made for this question was that the <a> tag titled "List of Academy Awards
 ceremonies" takes us to a wikipedia page with a table that includes links to Wikipedia pages for every single
 Academy Awards and that 1929 was the first year that the academy awards took place. Then I assumed that the first <a>
 tag in each row for this table took us to that specific year's Academy awards. Lastly, I assumed that on the specific
 Wikipedia page for an Academy awards ceremony, the table with the information on awards had each film in an <i> tag.
 The function nominatedMinimumNumberOfAwards() is responsible for dealing with the ouput of this question and takes
 the minimum number of nominations and year as input.

 Question 4: Question 4 is about finding the film that won a specific award in a specific year. The assumptions
 that I made for this question is that  <a> tag titled "List of Academy Awards ceremonies" takes us to a wikipedia page
 with a table that includes links to Wikipedia pages for every single Academy Awards and that 1929 was the first year
 that the academy awards took place. Then I assumed that the first <a> tag in each row for this table took us to that
 specific year's Academy awards. Then I assumed that, in the Wikipedia page for the specific years Academy award
 ceremony, the row that had the name of the award that we were looking for was followed by a row with the information
 on the films that won or were nominated for that award. I assumed that the winning award was in a <b> and was the
 first <a> inside it for this specific row and column under the title. The function filmWonSpecificAwardInYear() is
 responsible for dealing with the output of this question and takes the award category name and the year as input.

 Question 5: Question 5 is about finding the specific box office and budget for a film that won the award for in a
 specific category within 2022. The assumptions that I made for this question is that  <a> tag titled "List of Academy
 Awards ceremonies" takes us to a wikipedia page with a table that includes links to Wikipedia pages for every single
 Academy Awards and that 1929 was the first year that the academy awards took place. Then I assumed that the first <a>
 tag in each row for this table took us to that specific year's Academy awards. Then I assumed that, in the Wikipedia
 page for the specific years Academy award ceremony, the row that had the name of the award that we were looking for
 was followed by a row with the information on the films that won or were nominated for that award. I assumed that the
 winning award was in a <b> and was the first <a> inside it for this specific row and column under the title. Then I
 assumed that this <a> included a link to the Wikipedia site for the movie and that this site included an information
 summary at the top left in a table that had rows with categories titled "Box Office" and "Budget" whose next column
 values included the values for these items for the specific film in question. The function
 budgetAndBoxOfficeForAwardWinnerInSpecificYear() is responsible for dealing with the output for this question and
 takes in the award category and year as input.

 Question 6: Question 6 is about finding the academic institution or university which has the highest number of
 alumni nominated for the a specific award category. The assumptions that I made for this The assumptions that I made
 for this question is that  <a> tag titled "List of Academy Awards ceremonies" takes us to a wikipedia page with a
 table that includes links to Wikipedia pages for every single Academy Awards and that 1929 was the first year that the
 academy awards took place. Then I assumed that the first <a> tag in each row for this table took us to that specific
 year's Academy awards. Then I assumed that, in the Wikipedia page for the specific years Academy award ceremony, the
 row that had the name of the award that we were looking for was followed by a row with the information on the films
 that won or were nominated for that award. I also assumed that all the tables had the title of the award as the same
 and that each table had the actors that were nominated for that award in an <a> inside of a <li> for the column and
 row in the table. Furthermore, I assumed that the <a> would include a link to the Wikipedia page for the specific
 actor which would include an information summary on the individual in a table at the top right of the page which
 included a row titled "alma mater" or "education" in which the first educational institution is the one that is
 considered. The function academicInstitutionWithMostNominationsForSpecificAward() is responsible for dealing with the
 output for this question and takes in the award category as input.

 Question 7: Question 7 is about finding the number of times that films from countries have been nominated for a
 specific award category in the past. The assumptions that I made for this The assumptions that I made for this
 question is that the <a> tag titled "List of Academy Awards ceremonies" takes us to a wikipedia page with a table that
 includes links to Wikipedia pages for every single Academy Awards and that 1929 was the first year that the academy
 awards took place. Then I assumed that the first <a> tag in each row for this table took us to that specific year's
 Academy awards. Then I assumed that, in the Wikipedia page for the specific years Academy award ceremony, the row that
 had the name of the award that we were looking for was followed by a row with the information on the films that won or
 were nominated for that award. Within the information, I assumed that each film was in an <li>, <a>, and <i> tag for
 each and that the <a> provided a link to a Wikipedia page for that movie which included an information summary for the
 film in a table that included the rows with the titles "Country" or "Countries" in which the first country was
 considered to be the country that the film was representing. The function numberOfTimesCountryNominatedOrWonAward() is
 responsible for dealing with the output for this question and takes the award category as input.

 Question 8: Question 8 is about finding the number of times that a specific actor or actress has been nominated for
 or won an Academy Award in the past. To do this, I assumed that all categories for any Academy Award, previous or
 current could be found in the "Discontinued Categories" section on the Academy Awards Page or in the table titled
 "List of current Awards of Merit categories by year introduced, sortable by category". Using this information, I
 assumed that the <a> tag titled "List of Academy Awards ceremonies" takes us to a wikipedia page with a table that
 includes links to Wikipedia pages for every single Academy Awards. Then I assumed that the first <a> tag in each row
 for this table took us to that specific year's Academy awards. Then I assumed that, in the Wikipedia page for the
 specific years Academy award ceremony, the row that had the name of the award that we were looking for was followed by
 a row with the information on the films that won or were nominated for that award. Then for every award category that
 we considered from earlier, the information on its nominees and winner in terms of actors can be found in an <li> and
 <a> tag in each section. The function getNumberOfTimesActorNominatedOrWonOscar() is responsible for dealing with
 output for this question and takes in the actor's name as input.

 Extra Credit Question 1: This question is about finding the films that won Best Picture, Best Director, Best Actor,
 and Best Actress in any year in the past Academy Awards. The assumptions that I made for this question is that
 the <a> tag titled "List of Academy Awards ceremonies" takes us to a wikipedia page with a table that
 includes links to Wikipedia pages for every single Academy Awards. Then I assumed that the first <a> tag in each row
 for this table took us to that specific year's Academy awards. Then I assumed that, in the Wikipedia page for the
 specific years Academy award ceremony, the row that had the name of the award that we were looking for was followed by
 a row with the information on the films that won or were nominated for that award. The first <li> and <a> were the
 specific film that won that award and this was the case for all four of these awards. The function
 getFilmsThatWerePartOfAllFourAwards() is responsible for the output of this question and takes in as input the four
 award categories that were given in the question along with the year that the first part of the question asked for.