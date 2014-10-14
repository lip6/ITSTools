chan q0 = [6] of {byte};
chan q1 = [6] of {byte};

active[1] proctype p0()
{
byte NextFrame;
byte AckExp;
byte FrameExp; 
byte r;
byte s;
byte nbuf;
byte i;
   do
   ::
      ((nbuf<3));
      nbuf = (nbuf+1);
      atomic {
      q1!NextFrame;
      q1!((FrameExp+3)%(3+1));
      }
      NextFrame = ((NextFrame+1)%(3+1));

   ::
      atomic {
      q0?r;
      q0?s;
      }
      if
      ::
         ((r==FrameExp));
         /*printf('MSC: accept 0\n',r);*/
         FrameExp = ((FrameExp+1)%(3+1));

      ::
         else;

      fi;
      do
      ::
         (((((AckExp<=s)&&(s<NextFrame))||((AckExp<=s)&&(NextFrame<AckExp)))||((s<NextFrame)&&(NextFrame<AckExp))));
         nbuf = (nbuf-1);
         AckExp = ((AckExp+1)%(3+1));

      ::
         else;
         break;

      od;

   ::
      timeout;
      NextFrame = AckExp;
      /*printf('MSC: timeout\n');*/
      i = 1;
      do
      ::
         ((i<=nbuf));
         atomic {
         q1!NextFrame;
         q1!((FrameExp+3)%(3+1));
         }
         NextFrame = ((NextFrame+1)%(3+1));
         i = (i+1);

      ::
         else;
         break;

      od;

   od;
}
active[1] proctype p1()
{
byte NextFrame;
byte AckExp;
byte FrameExp; 
byte r;
byte s;
byte nbuf;
byte i;
   do
   ::
      ((nbuf<3));
      nbuf = (nbuf+1);
      atomic {
      q0!NextFrame;
      q0!((FrameExp+3)%(3+1));
      }
      NextFrame = ((NextFrame+1)%(3+1));

   ::
      atomic {
      q1?r;
      q1?s;
      }
      if
      ::
         ((r==FrameExp));
         /*printf('MSC: accept 1\n',r);*/
         FrameExp = ((FrameExp+1)%(3+1));

      ::
         else;

      fi;
      do
      ::
         (((((AckExp<=s)&&(s<NextFrame))||((AckExp<=s)&&(NextFrame<AckExp)))||((s<NextFrame)&&(NextFrame<AckExp))));
         nbuf = (nbuf-1);
         AckExp = ((AckExp+1)%(3+1));

      ::
         else;
         break;

      od;

   ::
      timeout;
      NextFrame = AckExp;
      /*printf('MSC: timeout\n');*/
      i = 1;
      do
      ::
         ((i<=nbuf));
         atomic {
         q0!NextFrame;
         q0!((FrameExp+3)%(3+1));
         }
         NextFrame = ((NextFrame+1)%(3+1));
         i = (i+1);

      ::
         else;
         break;

      od;

   od;
}