# Spark profiler fork

A fork of the well known [spark profiler](https://github.com/lucko/spark) which has support for CloudNet.

## Dependencies

This project is available via [jitpack](https://jitpack.io):

```groovy
repositories {
    maven {
        name = 'jitpack'
        url = 'https://jitpack.io/'
    }
}

dependencies {
    implementation('com.github.derklaro.spark', '<submodule>', 'master-SNAPSHOT')
}
```

## Building from source

After cloning the project you need to init the submodules and apply the patches using:

```
git submodule update --init && ./gradlew applyPatches
```

You can then publish spark into your local maven repository for testing:

* Navigate into the `patched-spark` folder using the command line
* Build & publish the project using `./gradlew publishToMavenLocal`

**Java 17 is required to build the project!**

## Making changes

After applying the patches a new folder should appear named `patched-spark`. You can make changes to the spark sources
in that folder. When you are done modifying them:

* Navigate into the `patched-spark` folder using the command line
* Commit your changes using a meaningful commit message
* Move into the root project folder and build a patch from the created commit using `./gradlew rebuildPatches`

## Modifying an existing patch

To modify an existing patch:

* First make your changes in the `patched-spark` folder as always
* Find the commit hash of the commit you want to edit by for example using `git log`, `git blame` or the git history
  provided by your IDE or GitHub
* Make a fixup commit: `git commit -a --fixup <commit hash of the patch to edit>`
    * You can also use `--squash` instead of `--fixup` to change the commit message of the patch as well
* Rebase the changes: `git rebase -i --autosquash origin`. This will automatically move the fixup to the right place,
  you just need to confirm the action by "saving" the changes in the text editor that will open.
* Move into the root project folder and rebuild the patch from the created commit using `./gradlew rebuildPatches`

## Patch formatting

In general, it is preferred to keep the patches as small as possible to make it easier to pull in changes made in the
forked repository. Some general notes:

* Single line changes always have a `// cloudnet` suffix, optionally providing a description of the change
  like: `// cloudnet - private -> protected`
* Multi line changes start with `// cloudnet start` and end with `// cloudnet end`, the start message can optionally
  contain a reason like: `// cloudnet start - easier access to caption registry`

Don't comment lines out unless necessary, you can make the diff smaller by (for example) inserting an `if (true)`
before the code you want to prevent from happening like:

```java
public @NonNull String getMessage() {
    // cloudnet start - no more heavy operations
    if (true) return "hello world";
    // cloudnet end
    return "hello" + " " + "world";
}
```

This is how a full change might look like:
```java
public @NonNull String getMessage(final @NonNull String input) { // cloudnet - private -> public
    final String partiallyFixed = input.replace('.', '-');
    final boolean empty = partiallyFixed.isBlank(); // cloudnet - was isEmpty but isBlank is better
    final Integer parsedValue = Ints.tryParse(partiallyFixed);
    // cloudnet start - no more heavy operations
    if (true) return "hello world " + partiallyFixed;
    // cloudnet end
    return "hello" + " " + partiallyFixed + " " + parsedValue + " " + "world";
}
```

## Contributing

If you think that something else should be changed in order to easier integrate this fork into your project, fell free
to open a pull request. Contributions are always welcome.

## License

This project is based on [spark](https://github.com/lucko/spark) which is licensed under the terms of 
the [GPL-3.0 license](https://github.com/lucko/spark/blob/master/LICENSE.txt). This 
includes all files pulled in from the `spark` git submodule as well as the patched code in `patched-spark`. The patches 
and all other files in this repository are MIT licensed to the CloudNetService team & contributors.