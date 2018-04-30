import { NativeModules } from 'react-native';

const { RNUtilities } = NativeModules;
type Props = {
  url: string,
  message: string,
  subject: string
};

export default {
  ...RNUtilities,
  share: (options: Props, title) =>
    new Promise((resolve, reject) => {
      try {
        CommonUtils.share(options, title);
        resolve({
          success: true,
          method: 'unknown'
        });
      } catch (error) {
        reject(error);
      }
    })
};
