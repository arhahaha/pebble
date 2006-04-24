package net.sourceforge.pebble.plugin.decorator;

import net.sourceforge.pebble.domain.BlogEntry;

/**
 * Disables TrackBacks for the blog entry.
 * 
 * @author Simon Brown
 */
public class DisableTrackBacksDecorator extends BlogEntryDecoratorSupport {

  /**
   * Executes the logic associated with this decorator.
   *
   * @param chain   the chain of BlogEntryDecorators to apply
   * @param context     the context in which the decoration is running
   * @throws BlogEntryDecoratorException
   *          if something goes wrong when running the decorator
   */
  public void decorate(BlogEntryDecoratorChain chain, BlogEntryDecoratorContext context)
      throws BlogEntryDecoratorException {
    BlogEntry blogEntry = context.getBlogEntry();
    blogEntry.setTrackBacksEnabled(false);
    
    chain.decorate(context);
  }

}
