# react-native-utilities

## Getting started

`$ npm install react-native-utilities --save`

### Mostly automatic installation

`$ react-native link react-native-utilities`

### Manual installation

#### iOS

1.  In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2.  Go to `node_modules` ➜ `react-native-utilities` and add `RNUtilities.xcodeproj`
3.  In XCode, in the project navigator, select your project. Add `libRNUtilities.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4.  Run your project (`Cmd+R`)<

#### Android

1.  Open up `android/app/src/main/java/[...]/MainActivity.java`

* Add `import vn.agiletech.rnutils.RNUtilitiesPackage;` to the imports at the top of the file
* Add `new RNUtilitiesPackage()` to the list returned by the `getPackages()` method

2.  Append the following lines to `android/settings.gradle`:
    ```
    include ':react-native-utilities'
    project(':react-native-utilities').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-utilities/android')
    ```
3.  Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    ```
      compile project(':react-native-utilities')
    ```

#### Windows

[Read it! :D](https://github.com/ReactWindows/react-native)

1.  In Visual Studio add the `RNUtilities.sln` in `node_modules/react-native-utilities/windows/RNUtilities.sln` folder to their solution, reference from their app.
2.  Open up your `MainPage.cs` app

* Add `using Utilities.RNUtilities;` to the usings at the top of the file
* Add `new RNUtilitiesPackage()` to the `List<IReactPackage>` returned by the `Packages` method

## Usage

```javascript
import RNUtilities from 'react-native-utilities';

// Share action
RNUtilities.share({ url, message, subject })
  .then(result => console.log('share', result))
  .catch(e => console.log('err', e));

// static informations:
console.log(RNUtilities);
{
  appVersion: "1.0",
  buildVersion: 1,
  bundleIdentifier: "vn.agiletech.demo",
  country: "United States",
  countryCode: "US",
  locale: "en",
}
```
