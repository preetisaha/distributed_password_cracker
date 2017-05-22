# distributed_password_cracker

<strong>Project Overview:</strong>
This project is based on cracking a distributed password through SHA1. It performs the permutation to generate all the required words.

<strong>Main Class:</strong> 
main class is the entry point of the project. It has a main method from within the main method we are creating a master thread and starting the same.

<strong>Master Class:</strong>
It implements the runnable interface, we override the run method with in that we initiated a master helper thread which generates all the combinations of words and pushes it in a queue. The master thread retrieves those back from the queue and sequentially hands it over to the worker thread by pushing it any of the worker queue.

<strong>Worker Class:</strong>
Each of the worker thread receives their share of the words and computes SHA1 and verifies the output to the one provided by the user. If it finds a match it displays the same and sets the complete flag. 

<strong>Permutation Used:</strong>
permutation used to generate all the required words.

				n=1
				∑ nPr
				n=10

r= Length of the word. <br>
n= Total number of alphabets.
