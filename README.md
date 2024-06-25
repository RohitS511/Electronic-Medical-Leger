### Medical Record Ledger System

## Project Members
Akshit Rathi - 2020A7PS2045H
Rohit Ranjan Sahu - 2020B4AA2380H
Arkodipto Dutta - 2020B4A72177H
Meghna Reddy Yadma - 2021A3PS2762H


### Problem Statement
In critical medical situations, doctors often struggle to access a patient's complete medical history due to:

Limited access to the patient's medical records across different hospitals.
Dependency on patients to carry their medical documents.

### Proposed Solution
We propose a Medical Record Ledger System where:
Medical records are cryptographically stored in a distributed ledger accessible to all stakeholders.
Patients can access their medical records through a secure digital 'MedWallet' using a private key.

### System Components
1. Ledger Management
mineBlock(): To verify and add a new block to the ledger.
createBlock(): To create a new block containing 'n' number of transactions.
A block is verified by:
PrevHash + CurrentData + K

2. MedWallet Access
viewUser(): Displays the public key of each MedWallet.
Access to the MedWallet is granted when the correct private and public key combination is used.

3. Transaction Verification
verifyTransaction(): Generates a signature using the private and public keys to verify the authenticity of the request.


### Running the Code
Follow the steps below to run the code:

# Setup:
Open the project folder in your preferred IDE.
Navigate to the Login.java file.
# Compile and Run:
Compile the Login.java file.
Run the compiled file.
# Login and Signup:
A pop-up login window will appear.
First, sign up as a new user.
Your credentials will be stored in the userDB.txt file located in the Master folder.
Log in using your new credentials.
# Adding a Diagnosis:
Add your recent diagnosis using the token number created during signup.
# Viewing Diagnosis History:
Click on your diagnosis to view your complete medical history. Your details will be displayed in the terminal.
# Exiting the Application:
Click on the 'Quit' button to exit the application.