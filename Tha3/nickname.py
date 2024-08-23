class N:
 def __init__(s,k):
  s.k,s.h,s.l,s.r,s.c=k,1,None,None,1
class A:
 def __init__(s):s.r=None
 def h(s,n):
  if n: return n.h
  return 0
 def u(s,n):
  if n:
   n.h=max(s.h(n.l),s.h(n.r))+1
 def R(s,n):
  l=n.l
  n.l=l.r
  l.r=n
  s.u(n)
  s.u(l)
  return l
 def L(s,n):
  r=n.r
  n.r=r.l
  r.l=n
  s.u(n)
  s.u(r)
  return r
 def b(s,n):
  if n: return s.h(n.l)-s.h(n.r)
  return 0
 def i(s,n,k):
  if n is None or k==n.k:return N(k)
  c=k<n.k
  if c:n.l=s.i(n.l,k)
  else:n.r=s.i(n.r,k)
  s.u(n)
  d=s.b(n)
  if d>1:
   if k<n.l.k:return s.R(n)
   else:n.l=s.L(n.l);return s.R(n)
  elif d<-1:
   if k>n.r.k:return s.L(n)
   else:n.r=s.R(n.r);return s.L(n)
  return n
 def f(s,n,k):
  if n is None or k==n.k:return n
  if k<n.k:return s.f(n.l,k)
  return s.f(n.r,k)
def m():
 n=int(input())
 l=10
 t=[A() for _ in range(l)]
 for _ in range(n):
  x=input()
  for i in range(len(x)):
   s=x[:i+1]
   if t[i].f(t[i].r,s) is None:t[i].r=t[i].i(t[i].r,s)
   else:t[i].f(t[i].r,s).c+=1
 q=int(input())
 for _ in range(q):
  x=input()
  l=len(x)
  r=t[l-1].f(t[l-1].r,x)
  if r:print(r.c)
  else:print(0)
m()
