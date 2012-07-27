Usage: java -jar reindeer-detabelize-0.0.2.jar [options]
  Options:
    -d, --detabelize   The files will be detabelized with the given detabelizer.
    -e, --excel        If tagging, an excel file containing some statistic will
                       be created.
  * -f, --files        File ending. Only files with this ending will be
                       processed. Mind: no leading point!
        --help         
                       Default: false
  * -i, --input        Specifies the path to input folder (there lay the files
                       to treat).
  * -o, --output       Specifies the path to the output folder (if a save option
                       has been chosen, files will be saved there) .
    -s, --save         Saving options. Valid are: none, detabelized, tagged.
                       Just valid if the associated option was chosen, too.
                       Default: detabelized
    -t, --tag          The files will be tagged with the given tagger. Allowed:
                       german, english
    -v, --verbose      Verbose mode. Prints out some more information about
                       every the processing steps.
                       Default: false
    -b, -beautify      Beautifies the saved data. Multiple whitespaces will be
                       reduced to one.
                       Default: true
                       
Actions:
-------------------
--detabelize num<INSERT NUM>, e.g. --detabelize num42
--detabelize sentence

--tag german
--tag english
                       
Remarks:
-------------------

1) java -jar reindeer-detabelize-0.0.2.jar --help shows the option page
2) Using a save option but ommiting the corresponding action will not save anything (e.g. --save tagged without -t )
3) There is no order needed for the options ( --detabelize num3 -t german is identical to -t german --detabelize num3)
4) Long and short arguments are identical ( --detabelize num3 is identical to -d num3)


Examples:
-------------------

############ Just Detabelizing ##############

-f		File ending:			pdf				Text will be extracted
-i		Input folder: 			testdata/in
-o		Output folder:			/tmp
-v		Verbose
-d		Detabelize Action:		num4			max 4 numbers/sentence allowed

-s is not specified, therefore default detabelized

Command:

java -jar reindeer-detabelize-0.0.2.jar -f pdf -i testdata/in -o /tmp -v -d num4



############ Just tagging ##################

-f		File ending:			pdf				Only xml files will be used
-i		Input folder: 			testdata/in
-o		Output folder:			/tmp
--tag 	Tagging Action:			german			German POS model will be used
--save 	Save Action:			tagged			Tagged data will be saved to output folder

Command:

java -jar reindeer-detabelize-0.0.2.jar -f pdf -i testdata/in -o /tmp --tag german --save tagged



############ Tagging and Detabelizing #######

-f				File ending:			xml				Only xml files will be used
-i				Input folder: 			testdata/in
-o				Output folder:			/tmp
--verbose
--tag 			Tagging Action:			english			English POS model will be used
--detabelize 	Detabelize Action:		sentence		Only sentences starting with capital letter and ending with . or ? or ! will survive this detabelizer.
--save 			Save Action:			detabelized		Detabelized data will be saved to output folder

java -jar reindeer-detabelize-0.0.2.jar -f xml -i testdata/in -o /tmp --verbose --tag english --detabelize sentence --save detabelized
