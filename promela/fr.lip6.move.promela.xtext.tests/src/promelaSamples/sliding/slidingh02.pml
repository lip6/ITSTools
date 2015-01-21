chan q0 = [3] of {Message};
chan q1 = [3] of {Message};

active[1] proctype p0()
{
byte NextFrame;
byte AckExp;
byte FrameExp; 
Message T;
byte nbuf;
byte i;
   do
   ::
      ((nbuf<3));
      nbuf = (nbuf+1);
      atomic {
 T.r = NextFrame; T.s = ((FrameExp+3)%(3+1));
      q1!T;
      }
      NextFrame = ((NextFrame+1)%(3+1));

   ::
      atomic {
      q0?T;
      }
      if
      ::
         ((T.r==FrameExp));
         /*printf('MSC: accept 0\n',r);*/
         FrameExp = ((FrameExp+1)%(3+1));

      ::
         else;

      fi;
      do
      ::
         (((((AckExp<=T.s)&&(T.s<NextFrame))||((AckExp<=T.s)&&(NextFrame<AckExp)))||((T.s<NextFrame)&&(NextFrame<AckExp))));
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
         atomic {T.r=NextFrame;T.s = ((FrameExp+3)%(3+1));
         q1!T;
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
Message T;
byte nbuf;
byte i;
   do
   ::
      ((nbuf<3));
      nbuf = (nbuf+1);
      atomic {
 T.r = NextFrame; T.s = ((FrameExp+3)%(3+1));
      q0!T;
      }
      NextFrame = ((NextFrame+1)%(3+1));

   ::
      atomic {
      q1?T;
      }
      if
      ::
         ((T.r==FrameExp));
         /*printf('MSC: accept 1\n',r);*/
         FrameExp = ((FrameExp+1)%(3+1));

      ::
         else;

      fi;
      do
      ::
         (((((AckExp<=T.s)&&(T.s<NextFrame))||((AckExp<=T.s)&&(NextFrame<AckExp)))||((T.s<NextFrame)&&(NextFrame<AckExp))));
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
         atomic {T.r=NextFrame;T.s = ((FrameExp+3)%(3+1));
         q0!T;
         }
         NextFrame = ((NextFrame+1)%(3+1));
         i = (i+1);

      ::
         else;
         break;

      od;

   od;
}