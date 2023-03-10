# JFLAP

This is a fork of Susan Rodger's JFLAP 7.1, which may be found at <https://www.jflap.org/>. This fork maintains the license of the original and is available free of charge.

## Building and Installing

```bash
cd /path/to/JFLAP
./gradlew build
java -jar ./build/libs/JFLAP.jar
```

If the build fails, check to make sure that the version of Gradle and the version of Java that you're running are compatible ([Compatibility Matrix](https://docs.gradle.org/current/userguide/compatibility.html)). The Gradle version can be found by running `./gradlew --version`.

## Changelog

- On a Multiple Run, when the "X configurations have been generated..." dialog appears, pressing the cancel button or closing the dialog cancels the current run and all subsequent runs.
- Add 'Yes to all' option to Multiple Run warning dialog. When the warning dialog is thrown after some number of configurations, the 'Yes to all' option increases the number of configurations before the warning dialog to that number of configurations + WARNING_STEP for all subsequent inputs

## Original README

```
Thanks for your interest in JFLAP!

We are distributing both the JFLAP source and binary under the JFLAP 7.1 license, available in the file LICENSE.
Summary of the license: The license text must be included in any distribution of JFLAP. Any distribution of JFLAP or any work that includes it modified or unmodified must be available free of charge.

Please send us feedback as to how you ended up using JFLAP, as well as any changes you would like to see in the official version. Please send this to jflap@cs.duke.edu

We are working on setting up a version controlled repository, so that more people can get more directly involved in the development of JFLAP.
Once this is up, we will announce it on www.jflap.org, together with details on how to become a privileged contributor with commit rights.
If you are interested in participating, please keep an eye on the website, or email jflap@cs.duke.edu indicating this interest.


Regards,

Susan Rodger
```
