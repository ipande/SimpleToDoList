# Pre-work - A simple ToDo list

Simple ToDo List is an android app that allows building a todo list and basic todo items 
management functionality including adding new items, editing and deleting an existing item.

Submitted by: Ishan Pande

Time spent: 4.5 hours spent in total

Completed user stories:

 Required: User can **successfully add and remove items** from the todo list
 Required: User can **tap a todo item in the list and bring up an edit screen for the todo 
 			item** and then have any changes to the text reflected in the todo list.
 Required: User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are implemented:

 Optional: Persist the todo items using SQLite (Active Android ORM) instead of a text file
 Optional: Use a [DialogFragment] instead of new Activity for editing items

## Video Walkthrough 

Here's a walkthrough of implemented user stories:

<img src='https://drive.google.com/file/d/0B1-dBqlw2L4qX2VaRk1qWTFjdHM/view?usp=sharing' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Notes
For a small app like this, which does not fully use the CRUD properties of SQL,
I felt that using SQLite was an overkill. I had initially implemented persistance using
sharedPrefs, but since this was approach was prescribed as one of the preferred ones,
I decided to implement it.


## License

    Copyright [Ishan Pande] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.