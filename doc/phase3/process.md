

##Organization
For this phase of the project, we decided to use the best of Kanban and of Scrum in order to produce a good product.
On the previous phase we produced a list of user stories. We used those stores as basis of all the functionalities we needed to implement.

We prioritized them and put them in a nice document:
https://docs.google.com/document/d/14yyOQfxSQ9Gc6b999yjpkjSEv9L1vV1bedX_XP4sGRo/edit?usp=sharing

We also used the Kanban board from https://kanbanflow.com/ for organization, because it provides a simpler way to organize tasks than other tools. It is easy to use and it offers excellent visualization of the project workflow. It makes it very clear which tasks need to be done or assigned and which tasks are in progress. Once an issue is created on github, it is then added to the Kanban board under the "To-do" board. We used different colours for tasks for better clarity: yellow for front end tasks, green for back end tasks, red for documentations/warnings/tips, and blue for other issues. Next, the tasks are assigned accordingly, and finally, they are moved to the "Done" board when they are finished.

![alt tag](https://github.com/lemonjump/project-team9-L0101/blob/master/doc/phase3/snapshot1.png)

##Phase 3 Meeting Minutes 
We mostly communicate online and we had a total of three face to face meetings for phase 3. The following are the meeting minutes.

>Meeting #1 

>Location:	BA2110

>Date:	Thurs Nov 5, 2015

>Time:	12:00 PM

>Attendance: Daniel, Maggie, Yunhao

Initial planning of phase 3 of the project. 
We Considered different tools and methods for organizing and decided to use Kanban board for visualizing workflow.





>Meeting #2 

>Location:	BA

>Date:	Fri Nov 13, 2015

>Time:	4:00 PM – 6:10 PM

>Attendance: Daniel, Shaun, Raven, Maggie, Joey

1. Discussed the problems that were passed on from phase 2. 
   - we used mySQL for database, but it is not compatible with android, therefore we decided to use SQLite instead.
2. Assigned tasks to group member
   - Maggie: create the database schema with SQLite
   - Daniel: create backlog
   - Shaun: switch database from mySQL to SQLite
3. Added new functionality with corresponding front end interface
   - enable user to reset the database
4. Redetermined the function prototypes from phase 2
   - updateCheck(int PID, bool check) returns bool;
   - AddProductShoppingList(String name) returns PID;
   - AddNewProduct(String name) returns PID;
   - DeleteProductShoppingList(int PID) returns bool;
   - getPIDbyName(String name) returns PID;
   - getSIDbyName(String name) returns SID;
5. Determined new function prototypes for phase 3
   - DoesStoreExist(String SName) returns SID;
   - AddNewStore(String SName) returns SID;
   - AddPurchase(PID, SID, Price, Quality, time) returns purchaseID;
   - GetAllPurchases(int PID) returns ArrayList<ProductHistory>;
   - GetMinPurchase(int PID, Quality) returns ProductHistory;
   - CreateShoppingTrip() returns ArrayList<ProductHistory>;
   - DeletePreviousPurchase(int purchaseId) returns bool;
   - DeleteHistoryOlderThan(String date) returns int; //number of deleted items
   




>Meeting #3 

>Location:	BA2110

>Date:	Fri Nov 20, 2015

>Time:	4:00 PM – 6:10 PM

>Attendance: Daniel, Shaun, Raven, Maggie, Joey, Yunhao



1. Discussion on how to combine the database and frontend interface
 - Will create a test method inside database class to test if the database actually work.
2. Discussion on what is left to do before the demo
 - Still have to implement all the database function in the backend.
 - have to create a object class which store the time,price and name of a product.
 - discuss on what kind of type we should pass to frontend from backend function(arraylist of object)
3.	Assigned the jobs
 - For coding part, Joey, Maggie and Shaun will take care of the function in the backend database
 Daniel and Raven will take care of the frontend interface. Yunhao will take care how the connection
between frontend and backend.
 - For artifact, each one of the group are assigned with part of the artifact so the artifact of phase
 can be taken care of before Monday.
4. Discuss on the further value of the product and how can we make it more valuable.
  - If we have time before the demo, we will try to add the our app to cloud system so users can share their experience
  and data through the cloud

##Explanation
The simplicity of Kanban and the quality of the documentation produced by the Scrum processes motivated us to create this hybrid system. Being the university students that we are, we needed to organize ourselves in a way that we could all work efficiently and at the same time produce a quality product, a way where everyone understood what they had to do and where everyone produced a similar amount of work.
