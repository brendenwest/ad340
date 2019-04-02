**Git Cheat Sheet**

**Installation**

1. Install git on your development computer (if not already done), using the instructions at https://git-scm.com/book/en/v2/Getting-Started-Installing-Git.
2. Open a terminal on your dev computer and **Set up your identity** & preferred text editor (optional) according to instructions here - https://git-scm.com/book/en/v2/Getting-Started-First-Time-Git-Setup 
3. Navigate to your project folder and initialize a local repo:
::
    $ git init 
4. Connect to a remote Github.com repo to your local repo:
::
    $ git remote add <alias> <remote_url>

**Commiting changes to Github **
Git has a great variety of options, so it's important to use the simplest workflow possible. The most basic steps for committing your changes to Github are: 

1. Check local repo status:
::
    git status
    
2. Add all your changes to the local repo:
::
    git add .

3. Commit your changes to the local repo:
::
    git commit -m "COMMIT MESSAGE"

4. Pull changes from the remote repo (in case your local PC isn't current):
::
    git pull origin master

5. Push local changes to the remote repo:
::
    git push origin master

(by default, remote repo is called 'origin' and local repo branch is 'master')

Other Useful Git commands - https://education.github.com/git-cheat-sheet-education.pdf
