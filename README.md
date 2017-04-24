# Litho Lint Rules

Litho Lint rules is a project that contains Lint rules generated according to [Litho Best Practices](http://fblitho.com/docs/best-practices). It contains 5 rules:

* `AnnotatedMethodVisibility` - Checks whether a lifecycle method has the proper visibility (package-private & static)

* `ComponentContextName` - Checks whether `ComponentContextName` on a lifecycle method is named as `c` for less code verbosity and more readability

* `LayoutSpecClassName` - Checks whether a `@LayoutSpec` or `@MountSpec` annotated class is properly named

* `OptionalPropBeforeRequired` - Checks whether a lifecycle method follows the suggested order of `Props` (required ones before optional)

* `PossibleResourceType` - Suggests possible use of a `ResType` for `float` or `Drawable` Prop annotated parameters (can add more if there is a request :) )

## Usage

Copy [litho-lint-v1.0.jar](https://github.com/pavlospt/litho-lint-rules/blob/master/jar-releases/litho-lint-v1.0.jar) and place it on your `.android/lint` folder. If you do not have a `lint` folder, you may create one.

Run `Code Inspection` through Android Studio or just run `./gradlew clean :app:lint`

## Other

Litho repository: https://github.com/facebook/litho

Litho documentation: http://fblitho.com/docs/getting-started

License
=======

    MIT License

    Copyright (c) 2017 Pavlos-Petros Tournaris

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
