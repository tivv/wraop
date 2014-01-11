WrAOP wrapper building library
==============================

Introduction
------------

How many times have you created a wrapper class that delegates all the
methods to it's delegate, while adding some functionality in few places?
I've done this many times. This library would allow you to do this easily.

Another option to use it is when you need to provide plugin functionality
for your library or application. Now you don't need to create chain of
responsibility or "pre" and "post" interfaces simply for extension points.
Simply allow your users to register aspects and wrap your objects with
the help of this library.

Comparison to other options
---------------------------

- Spring AOP.

  Actually this library is a wrapper over Spring AOP. If you can use
Spring AOP directly, just do it. But not every object is in context.
And this library allows to apply your wrapper at much more granular
fashion. You directly say which objects you'd like to wrap

- AspectJ

  AspectJ is much more global thing. It enhances your classes and
prefers to do this in compile time. This library creates you proxies,
and you can use AspectJ annotations to do the work.

Requirements
------------

 - Java 6
 - Spring AOP

Optional dependencies
---------------------

 - cglib - to make faster proxies
 - AspectJ weaver - to use AspectJ annotations

Quick start
-----------

     new WrapperFactoryBuilder().<TargetClass>build()
        .withAspects(aspect1, aspect2, aspect3).wrapAllInterfaces(target)

 That's all. You can reuse the factory with aspects registered to wrap
 multiple objects. It's thread-safe. It will autodetect aspect types
 (Spring/AOP alliance/AspectJ). Here is an example aspect from tests:

     @Aspect
     public class ConstantReturningAspect {
         private final Object constant;

         public ConstantReturningAspect(Object constant) {
             this.constant = constant;
         }

         @Around("execution(* im.tym.wraop.data.Transformer.transform(..))")
         Object returnConstant() {
             return constant;
         }
     }

 Easy, is not it?

Details
-------

 For WrapperFactory, please see it's javadoc.
 As of aspect types, please refer to Spring AOP documentation:
 http://docs.spring.io/spring/docs/3.2.3.RELEASE/spring-framework-reference/html/aop.html#aop-introduction

Contacts
--------

 Project home page: https://github.com/tivv/wraop You can submit bugs,
    feature requests or pull requests there.

 Author contact: http://blog.vit.tym.im
