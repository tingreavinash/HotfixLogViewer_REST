# Instagram Chat Reader

## Description

This application will read the ***messages.json*** file which you get in Instagram data backup and displays the conversation on webpage at given port.

## How To Use

1. Using ***application.yml*** file:
    - Specify the instagram user id for property `participant`
    - Specify the location of ***messages.json*** file for property `messageFile`

2. Using command line argument:
    - While running application, provide the argument `--participant_id=<insta_user_id>` 
    ex. java -jar InstagramChatReader.war --participant_id=avinash_tingre
    - Specify the path of `messages.json` file in ***application.yml*** file
