Implements a map from keys to integer counts using somthing similar to Iacono's working set structure: a list of trees where each tree is smaller than and contains more recently used keys than the trees after it in the list (except that the last tree may be smaller than the next-to-last tree).

A generic class called BSTWithLRU that implements the CountMapWithLRU interface. 


A generic class called ListOfCountMapsWithLRU that implements the CountMap interface. This class should maintain a list of BSTWithLRU objects. 

For full credit, your solution must meet the following requirements:


FEATURES

1. The add, update, remove, removeLRU, and get methods implemented in BSTWithLRU run in O(logn) average time.
2. The size method run in O(1) time and the addToList method runs in O(n) time. 
3. The number of trees used increases with the number of words contained in the inputed text file. 
4. Keys are dynamically moved between trees as they are accessed.


FILES

1. BSTWithLRU.java - A binary search tree modified to incorporate least a recently used queue based on elements accessed in the tree, which implements the CountMapWithLRU.java interface  
2. CountMap.java - The interface for a frequency list (or a map from keys to integer frequencies), which is implemented by ListOfCountMapsWithLRU.java
 can be added or removed and their frequencies can be increased by one.
3. CountMapWithLRU.java - Interface for a map from keys to integer counts, implemented by BSTWithLRU.java 
4. ListOfCountMapsWithLRU.java - A frequency list (or a map from keys to integer frequencies).  Keys can be added or removed and their frequencies can be increased by one, which implements CountMap.java 
5. WordCount.java - The class which contains the main method of the repository, which takes a single command line argument - the name of any arbitrary text file and outputs word counts for the top one-hundred most frequent words in said text. 

6. invisman.txt - A sample arbitrary text. Namely, the novel by Ralph W. Ellison, "Invisible Man."


EXAMPLE

--------------------------INPUT--------------------------

java WordCount invisman.txt 

--------------------------OUTPUT-------------------------

the=3274
and=2035
a=1334
of=1299
to=980
he=961
i=901
in=746
was=727
his=708
said=532
it=505
that=435
with=356
you=340
had=325
at=323
my=285
as=278
but=268
for=268
on=267
him=256
then=245
man=229
mr=217
up=210
kemp=207
me=204
an=177
invisible=174
out=174
there=174
all=173
down=170
is=165
door=164
one=164
by=162
not=162
have=153
she=151
into=150
this=150
hall=148
be=144
were=144
came=143
no=140
went=140
from=133
they=133
its=132
so=131
what=131
again=118
little=115
her=114
over=114
about=113
marvel=111
some=106
room=98
or=93
very=93
been=92
if=92
voice=92
back=90
could=90
face=90
do=88
like=88
did=86
would=86
mrs=84
saw=82
stood=78
made=77
must=77
suddenly=77
heard=76
them=75
after=73
window=73
through=72
are=70
get=70
now=70
see=70
when=69
dont=68
time=68
house=67
hand=66
more=66
still=65
know=63
moment=62
two=62

