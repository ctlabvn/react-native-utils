
#import "RNUtilities.h"
#import <React/RCTBridge.h>

@implementation RNUtilities
@synthesize bridge = _bridge;

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

RCT_EXPORT_MODULE()

- (NSDictionary *)constantsToExport {
  NSString *language = [[[NSBundle mainBundle] preferredLocalizations] objectAtIndex:0];

  NSLocale *locale = [NSLocale currentLocale];
  NSString *countryCode = [locale objectForKey: NSLocaleCountryCode];

  NSLocale *usLocale = [[NSLocale alloc] initWithLocaleIdentifier:@"en_US"];
  NSString *country = [usLocale displayNameForKey: NSLocaleCountryCode value: countryCode];

  return @{@"appVersion"  : [[NSBundle mainBundle] objectForInfoDictionaryKey:@"CFBundleShortVersionString"],
           @"buildVersion": [[NSBundle mainBundle] objectForInfoDictionaryKey:(NSString *)kCFBundleVersionKey],
           @"bundleIdentifier"  : [[NSBundle mainBundle] bundleIdentifier],
           @"locale": language,
           @"country": country,
           @"countryCode": countryCode
          };
}

- (void)loadBundle {
  [_bridge reload];
}

@end
  