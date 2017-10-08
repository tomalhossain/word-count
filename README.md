## Word Count Map

Implements a map from keys to integer counts using somthing similar to Iacono's working set structure: a list of trees where each tree is smaller than and contains more recently used keys than the trees after it in the list (except that the last tree may be smaller than the next-to-last tree).

A generic class called BSTWithLRU that implements the CountMapWithLRU interface.

A generic class called ListOfCountMapsWithLRU that implements the CountMap interface. This class should maintain a list of BSTWithLRU objects.

### Features

1. The add, update, remove, removeLRU, and get methods implemented in BSTWithLRU run in O(logn) average time.
2. The size method run in O(1) time and the addToList method runs in O(n) time.
3. The number of trees used increases with the number of words contained in the inputed text file.
4. Keys are dynamically moved between trees as they are accessed.

### Files

1. BSTWithLRU.java - A binary search tree modified to incorporate least a recently used queue based on elements accessed in the tree, which implements the CountMapWithLRU.java interface
2. CountMap.java - The interface for a frequency list (or a map from keys to integer frequencies), which is implemented by ListOfCountMapsWithLRU.java
 can be added or removed and their frequencies can be increased by one.
3. CountMapWithLRU.java - Interface for a map from keys to integer counts, implemented by BSTWithLRU.java
4. ListOfCountMapsWithLRU.java - A frequency list (or a map from keys to integer frequencies).  Keys can be added or removed and their frequencies can be increased by one, which implements CountMap.java
5. WordCount.java - The class which contains the main method of the repository, which takes a single command line argument - the name of any arbitrary text file and outputs word counts for the top one-hundred most frequent words in said text.
6. invisman.txt - A sample arbitrary text. Namely, the novel by Ralph W. Ellison, "Invisible Man."

### Sample Process

--------------------------INPUT--------------------------

java WordCount invisman.txt

--------------------------OUTPUT-------------------------

the=3274<br>
and=2035<br>
a=1334<br>
of=1299<br>
to=980<br>
he=961<br>
i=901<br>
in=746<br>
was=727<br>
his=708<br>
said=532<br>
it=505<br>
that=435<br>
with=356<br>
you=340<br>
had=325<br>
at=323<br>
my=285<br>
as=278<br>
but=268<br>
for=268<br>
on=267<br>
him=256<br>
then=245<br>
man=229<br>
mr=217<br>
up=210<br>
kemp=207<br>
me=204<br>
an=177<br>
invisible=174<br>
out=174<br>
there=174<br>
all=173<br>
down=170<br>
is=165<br>
door=164<br>
one=164<br>
by=162<br>
not=162<br>
have=153<br>
she=151<br>
into=150<br>
this=150<br>
hall=148<br>
be=144<br>
were=144<br>
came=143<br>
no=140<br>
went=140<br>
from=133<br>
they=133<br>
its=132<br>
so=131<br>
what=131<br>
again=118<br>
little=115<br>
her=114<br>
over=114<br>
about=113<br>
marvel=111<br>
some=106<br>
room=98<br>
or=93<br>
very=93<br>
been=92<br>
if=92<br>
voice=92<br>
back=90<br>
could=90<br>
face=90<br>
do=88<br>
like=88<br>
did=86<br>
would=86<br>
mrs=84<br>
saw=82<br>
stood=78<br>
made=77<br>
must=77<br>
suddenly=77<br>
heard=76<br>
them=75<br>
after=73<br>
window=73<br>
through=72<br>
are=70<br>
get=70<br>
now=70<br>
see=70<br>
when=69<br>
dont=68<br>
time=68<br>
house=67<br>
hand=66<br>
more=66<br>
still=65<br>
know=63<br>
moment=62<br>
two=62
