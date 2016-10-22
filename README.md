
This project was created by Kazem Qazanfari as a part of his research in the field of data hiding. The purpose of implementing this project is to provide the embedding behavior of some baseline steganography methods including: LSB, LSB+ [LSBP], LSB++ [LSBPP], LSB Matching [LSBM], LSB Matching Revisited [LSBMR].

Actually, this project provides/simulates the embedding process of above steganography methods, which creates the exact statistical artifacts that are created by these steganography methods.

so, researchers in the field of data hiding [either steganography or steganalysis] will be able to compare their methods [steganography/steganalysis] to these base-line methods.


* So,1) we use a pseudo random message (using java random library) as the encrypted message that should be embedded; therefore, you do not need to provide the message.
*    2) by using a random process, we simulate the embedding key; therefore, you do not need to provide the embedding key.
*    3) we just provide the embedding process; therefore, you can use the stego images created by these methods and analyze them. 

*****************How to use this project*********************

Very easy: you just need to download CreateStego.class, then follow the Usage Instruction to use it [see below]. 

If there is any problem with this binary file [CreateStego.class] or you want to edit the source code, you might need to compile the source code again easily by javac [java compiler - Note: you should have jdk installed on your computer before]:
How to compile:

1. install jdk on your computer

2. make sure to have javac set in the system variable

3. open command prompt and run following command

4. javac CreateStego.java   

5. There you go, follow the Usage Instruction

*********************Usage Instruction************************

1. install jdk or jre on your computer [jdk has jre inside, if you installed jdk before, you do not need to install jre]

2. make sure to have java set in the system variable

3. If you want to run it in terminal, open the system terminal and run following command:

**java CreateStego MethodName FileNamePath MessegeLength**


**MethodName**: it should be one of following options:

* LSB: for simple LSB steganography method.

* LSBP: for LSB+ steganography method.

* LSBPP: for LSB++ steganography method.

* LSBM: for LSB matching steganography method.

* LSBMR: for LSB matching revisited steganography method.


**FileNamePath**: it should be the path and file name of the cover bitmap image.

   *If your file is in the current directory, you do not need to specify the path, just provide the file name.

   *If your file is not in the current directory, you need to specify the complete file path and name.


**MessageLength**: it should be an integer number which determines the message length in bit.

***Examples: ***

* java CreateStego LSB test.bmp 1000

* java CreateStego LSBP /User/kazemmit/Document/test.bmp 2000

* java CreateStego LSBPP /User/kazemmit/Document/test.bmp 1500

* java CreateStego LSBM /User/kazemmit/Document/test.bmp 2000

* java CreateStego LSBMR test.bmp 1000



However, If you want to call CreateStego in your project, just google it to find how you can call a java class [or jar file] in your project [any language].


***Copy right:***

 This project was created by Kazem Qazanfari as a part of his research in the field of computer science [Steganography].You are free to use or edit this code for your research. If you use this project or some parts of code, please kindly cite the following papers:

Qazanfari, Kazem, and Reza Safabakhsh. A new steganography method which preserves histogram: Generalization of LSB++. Information Sciences 277 (2014): 90-101.

Ghazanfari, Kazem, Shahrokh Ghaemmaghami, and Saeed R. Khosravi. LSB++: an improvement to LSB+ steganography. TENCON 2011-2011 IEEE Region 10 Conference. IEEE, 2011.


