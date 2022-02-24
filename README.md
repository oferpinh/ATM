# ATM
ATM Assignment for Tikal - Ofer Pinhas

This is my ATM-Assignment project that was developed in Java Spring Boot.

I've used swagger so it would be easier to call its APIs, and as you can see I've added a few admin api calls for monitoring the ATM's money state.
The project is deployed on gcloud under https://atm-zvvq636fhq-uc.a.run.app/swagger-ui.html#/

I really wanted to address the bonus section and add a mysql db to the project, but unfortunately I encountered a very time consuming obstacle when trying to deploy the application in the first iteration -
The root cause of this detour was a JNI based package (named google or-tools) for solving the withdrawal requirement, and while that made the development itself much easier, dockerizing the application as a linux machine (or a windows machine) had significant issues.
which made me eventually abandon the usage of this package and write the withdrawal solving independently as a recursive permutation-based algorithm.
Since this took longer than I can dedicate to this project, I've left the data management as is in memory.
If you find this as a crucial aspect of the assignment, please let me know.

Just for your curiosity, you can take a look at the original solver in a previous commit of the system - which views the withdrawal problem as a discrete variables constraint system:
https://github.com/oferpinh/ATM/blob/bcf7ad0ed0d77bbef4c56fa02fef539bc1a0f924/atm/src/main/java/tikal/atm/service/withdraw/resolver/ScipWithdrawResolver.java
This solution was more efficient, more robust and elegant.. but as I said, I couldn't dedicate any more time to it.
