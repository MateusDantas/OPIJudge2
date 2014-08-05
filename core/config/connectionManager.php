<?hh

require_once('dbConfig.php');

class ConnectionManager {
  
  private static $link = null;
  private static $dsn = null;

  public function __construct() {
    
    self::$dsn = Variables::$DB_DRIVER."dbname=".Variables::$DB_NAME.';'.'host='.Variables::$DB_HOST.';'.'port='.Variables::$DB_PORT;
    self::$link = self::getLink();
  }

  public static function getLink() {

    if (self::$link) {
      return self::$link;
    }
    
   
    self::$link = new PDO(self::$dsn, Variables::$DB_USERNAME, Variables::$DB_PASSWORD);
    
    return self::$link;
  }

}

$db_connection_manager = new ConnectionManager();