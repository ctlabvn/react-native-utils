import { NativeModules, ActionSheetIOS } from 'react-native';

const { RNUtilities } = NativeModules;

type Props = {
  url: string,
  message: string,
  subject: string
};

export default {
  ...NativeModules,
  share: (options: Props, title) =>
    new Promise((resolve, reject) => {
      ActionSheetIOS.showShareActionSheetWithOptions(
        {
          url: options.url,
          message: options.message,
          subject: options.subject
        },
        error => {
          reject(error);
        },
        (success, method) => {
          resolve({
            success,
            method
          });
        }
      );
    })
};
